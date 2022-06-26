package org.polik.polikmarket.repositories;

import org.polik.polikmarket.models.ShopUnitStatisticEntity;
import org.polik.polikmarket.utils.SQLUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Polik on 2/1/2022
 */
@Transactional(readOnly = true)
public interface ShopUnitStatisticRepository extends JpaRepository<ShopUnitStatisticEntity, UUID> {
    @Query(nativeQuery = true, value = SQLUtil.RECURSIVE_STATISTIC_SEARCH_QUERY)
    Set<ShopUnitStatisticEntity> findBetween(UUID id, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = SQLUtil.RECURSIVE_DELETE_QUERY)
    void delete(UUID id);

    boolean existsByUnitId(UUID unitId);
}
