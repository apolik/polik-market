package org.polik.polikmarket.repositories;

import org.polik.polikmarket.models.ShopUnitEntity;
import org.polik.polikmarket.models.ShopUnitType;
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

import static org.polik.polikmarket.exceptions.GlobalExceptionHandler.NO_SUCH_SHOP_UNIT_MESSAGE;
import static org.polik.polikmarket.utils.ValidationUtil.checkModification;

/**
 * Created by Polik on 2/1/2022
 */
@Transactional(readOnly = true) // +100500 к оптимизации:)
public interface ShopUnitRepository extends JpaRepository<ShopUnitEntity, UUID> {
    Optional<ShopUnitEntity> findByIdAndType(UUID id, ShopUnitType type);

    @Transactional
    @Modifying
    @Query("delete from ShopUnitEntity u where u.id=?1")
    int delete(UUID id);

    @Query("from ShopUnitEntity u where u.type='OFFER' and u.date between ?1 and ?2")
    Set<ShopUnitEntity> findAllByDateBetweenAndType(LocalDateTime startDate, LocalDateTime endDate, ShopUnitType type);

    @Transactional
    default void deleteExisted(UUID id) {
        checkModification(
                delete(id), String.format(NO_SUCH_SHOP_UNIT_MESSAGE, id)
        );
    }

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = SQLUtil.RECURSIVE_DATE_UPDATE_QUERY)
    void updateDate(@Param("date") LocalDateTime date);
}
