package org.polik.polikmarket.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.polik.polikmarket.models.shopunit.ShopUnit;
import org.polik.polikmarket.models.shopunit.ShopUnitEntity;
import org.polik.polikmarket.models.shopunit.ShopUnitType;
import org.polik.polikmarket.repositories.ShopUnitRepository;
import org.polik.polikmarket.utils.SQLUtil;
import org.polik.polikmarket.utils.ShopUnitUtil;
import org.polik.polikmarket.web.requests.ShopUnitImportRequest;
import org.polik.polikmarket.web.responses.ShopUnitStatisticResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.polik.polikmarket.utils.ShopUnitUtil.*;
import static org.polik.polikmarket.utils.ValidationUtil.checkModification;
import static org.polik.polikmarket.utils.ValidationUtil.rejectIfInvalidType;

/**
 * Created by Polik on 6/10/2022
 */
@Slf4j
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "shop-units")
public class ShopUnitServiceImpl implements ShopUnitService {
    public static final RowMapper<ShopUnit> MAPPER = new BeanPropertyRowMapper<>(ShopUnit.class);

    private final ShopUnitRepository repository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    @CacheEvict(allEntries = true)
    public void handleImport(ShopUnitImportRequest request) {
        log.info("handleImport {}", request);

        request.items().forEach(unitImport -> {
            rejectIfInvalidType(unitImport);
            ShopUnitEntity parent = getParent(unitImport.getParentId());

            repository.save(fromImport(
                    unitImport, request.updateDate(), parent
            ));
        });

        repository.updateDate(request.updateDate());
    }

    @Cacheable(key = "#id")
    public ShopUnit get(UUID id) {
        log.info("get {}", id);
        return ShopUnitUtil.setChildren(
                jdbcTemplate.query(SQLUtil.RECURSIVE_SEARCH_QUERY, MAPPER, id), id
        );
    }

    public ShopUnitStatisticResponse getOffersBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("getOffersBetween {} - {}", startDate, endDate);
        Set<ShopUnitEntity> units = repository.findAllByDateBetweenAndType(
                startDate,
                endDate,
                ShopUnitType.OFFER
        );

        return new ShopUnitStatisticResponse(
                toStatisticUnits(units)
        );
    }

    @CacheEvict(allEntries = true)
    public void delete(UUID id) {
        log.info("delete {}", id);
        checkModification(
                repository.delete(id),
                String.format("No such shop unit with id: %s", id)
        );
    }

    private ShopUnitEntity getParent(UUID parentId) {
        if (parentId == null) {
            return null;
        } else {
            return getOrThrowValidationException(
                    repository.findByIdAndType(parentId, ShopUnitType.CATEGORY), // parent cannot be OFFER
                    parentId,
                    ShopUnitType.CATEGORY
            );
        }
    }
}
