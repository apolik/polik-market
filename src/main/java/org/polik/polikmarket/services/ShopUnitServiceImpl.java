package org.polik.polikmarket.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.polik.polikmarket.dto.units.ShopUnit;
import org.polik.polikmarket.exceptions.NotFoundException;
import org.polik.polikmarket.models.ShopUnitEntity;
import org.polik.polikmarket.models.ShopUnitStatisticEntity;
import org.polik.polikmarket.models.ShopUnitType;
import org.polik.polikmarket.repositories.ShopUnitRepository;
import org.polik.polikmarket.repositories.ShopUnitStatisticRepository;
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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.polik.polikmarket.exceptions.GlobalExceptionHandler.NO_SUCH_SHOP_UNIT_MESSAGE;
import static org.polik.polikmarket.utils.ShopUnitUtil.*;
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
    private final ShopUnitStatisticRepository statisticRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void handleImport(ShopUnitImportRequest request) {
        log.info("handleImport {}", request);

        request.items().forEach(unit -> {
            rejectIfInvalidType(unit);
            ShopUnitEntity parent = getParent(unit.getParentId());

            repository.save(
                    fromImport(unit, request.updateDate(), parent)
            );
            statisticRepository.save(
                    new ShopUnitStatisticEntity(unit, request.updateDate())
            );
        });

        repository.updateDate(request.updateDate()); // Update date property for categories
    }

    @Override
    @Cacheable(key = "#id")
    public ShopUnit get(UUID id) {
        log.info("get {}", id);
        return ShopUnitUtil.setChildren(
                jdbcTemplate.query(SQLUtil.RECURSIVE_SEARCH_QUERY, MAPPER, id), id
        );
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(UUID id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
        statisticRepository.delete(id);
    }

    @Override
    public ShopUnitStatisticResponse getOffersBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("getOffersBetween {} - {}", startDate, endDate);
        Set<ShopUnitEntity> units = repository.findAllByDateBetweenAndType(
                startDate, endDate, ShopUnitType.OFFER
        );

        return new ShopUnitStatisticResponse(
                toStatisticUnits(units)
        );
    }

    @Override
    public ShopUnitStatisticResponse getStatisticBetween(UUID id, @Nullable LocalDateTime dateStart,
                                                         @Nullable LocalDateTime dateEnd) {
        log.info("getStatisticBetween {} - {}", dateStart, dateEnd);
        if (!statisticRepository.existsByUnitId(id)) {
            throw new NotFoundException(String.format(NO_SUCH_SHOP_UNIT_MESSAGE, id));
        }

        // fixme: работает криво
        Set<ShopUnitStatisticEntity> units = statisticRepository.findBetween(
                id, dateStart, dateEnd
        );

        return new ShopUnitStatisticResponse(
                toStatisticUnits2(units)
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
