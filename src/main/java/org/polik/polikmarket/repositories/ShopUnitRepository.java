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
//    @Query(nativeQuery = true, value = """ // too slow
//with recursive
//    unit_tree_parent as (SELECT s1.id,
//                                s1.name,
//                                s1.price,
//                                s1.date,
//                                s1.parent_id,
//                                s1.type
//                         FROM shop_unit s1
//                         WHERE s1.date = :date
//
//                         UNION ALL
//
//                         SELECT s2.id,
//                                s2.name,
//                                s2.price,
//                                s2.date,
//                                s2.parent_id,
//                                s2.type
//                         FROM shop_unit s2
//                                  JOIN unit_tree_parent ut ON ut.parent_id = s2.id),
//    unit_tree_children as (SELECT s1.id,
//                                  s1.name,
//                                  s1.price,
//                                  s1.date,
//                                  s1.parent_id,
//                                  s1.type,
//                                  0 as level,
//                                  array[id] as path
//                           FROM unit_tree_parent s1
//
//                           UNION ALL
//
//                           SELECT s2.id,
//                                  s2.name,
//                                  s2.price,
//                                  s2.date,
//                                  s2.parent_id,
//                                  s2.type,
//                                  level + 1,
//                                  ut.path ||  s2.id as path
//                           FROM shop_unit s2
//                                    JOIN unit_tree_children ut ON ut.id = s2.parent_id)
//
//UPDATE shop_unit u
//SET date  = :date,
//    price = cast(ap.avg_price as bigint)
//FROM unit_tree_children ut
//         LEFT JOIN LATERAL (
//    SELECT avg(ut2.price) avg_price
//    FROM unit_tree_children ut2
//    WHERE ut.level < ut2.level
//      and ut.id = any (path)
//    GROUP BY ut.id
//    ) ap ON TRUE
//WHERE u.type = 'CATEGORY'
//""")
    void updateDate(@Param("date") LocalDateTime date);
}
