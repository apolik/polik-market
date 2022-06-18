package org.polik.polikmarket.repositories;

import org.polik.polikmarket.models.shopunit.ShopUnitEntity;
import org.polik.polikmarket.models.shopunit.ShopUnitType;
import org.polik.polikmarket.utils.SQLUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Polik on 2/1/2022
 */
@Transactional(readOnly = true)
public interface ShopUnitRepository extends JpaRepository<ShopUnitEntity, UUID> {
    Optional<ShopUnitEntity> findByIdAndType(UUID id, ShopUnitType type);

    @Transactional
    @Modifying
    @Query("delete from ShopUnitEntity u where u.id=?1")
    int delete(UUID id);

    @Query("from ShopUnitEntity u where u.type='OFFER' and u.date between ?1 and ?2")
    Set<ShopUnitEntity> findAllByDateBetweenAndType(LocalDateTime startDate, LocalDateTime endDate, ShopUnitType type);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = SQLUtil.RECURSIVE_DATE_UPDATE_QUERY)
    void updateDate(@Param("date") LocalDateTime date);
}
