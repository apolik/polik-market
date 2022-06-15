package org.polik.polikmarket.utils;

import lombok.experimental.UtilityClass;

/**
 * Created by Polik on 6/15/2022
 */
@UtilityClass
public class SQLUtil {
    public static final String RECURSIVE_SEARCH_QUERY =
            """
                    with recursive unit_tree as (
                         SELECT
                             s1.id,
                             s1.name,
                             s1.price,
                             s1.date,
                             s1.parent_id,
                             s1.type,
                             0 as level,
                             array[id] as path
                         FROM
                           shop_unit s1
                         WHERE
                           s1.id = ?
                           
                         UNION ALL
                                                 
                         SELECT
                           s2.id,
                           s2.name,
                           s2.price,
                           s2.date,
                           s2.parent_id,
                           s2.type,
                           level + 1,
                           ut.path ||  s2.id as path
                         FROM
                           shop_unit s2
                           JOIN unit_tree ut ON ut.id = s2.parent_id
                     )
                                                 
                     SELECT
                       ut.id,
                       ut.name,
                       ut.parent_id,
                       ut.date,
                       ut.type,
                       case when ut.type = 'CATEGORY' then ap.avg_price else ut.price end as price,
                       ut.level,
                       ut.path
                                                 
                     FROM
                       unit_tree ut
                       LEFT JOIN LATERAL (
                         SELECT
                           avg(ut2.price) avg_price
                         FROM
                           unit_tree ut2
                         WHERE
                           ut.level < ut2.level
                           and  ut.id = any(path)
                         GROUP BY
                           ut.id
                       ) ap ON TRUE
                                                 
                     ORDER BY id
                     """;

    public static final String RECURSIVE_DATE_UPDATE_QUERY = """
            with recursive unit_tree as (
                         SELECT
                             s1.id,
                             s1.name,
                             s1.price,
                             s1.date,
                             s1.parent_id,
                             s1.type
                         FROM
                           shop_unit s1
                         WHERE
                           s1.date = :date
                           
                         UNION ALL
                                                 
                         SELECT
                           s2.id,
                           s2.name,
                           s2.price,
                           s2.date,
                           s2.parent_id,
                           s2.type
                         FROM
                           shop_unit s2
                           JOIN unit_tree ut ON ut.parent_id = s2.id
                     )
                     
                        UPDATE shop_unit u
                        SET date = :date
                        FROM unit_tree tr
                        WHERE u.id = tr.id
                       
            """;
}
