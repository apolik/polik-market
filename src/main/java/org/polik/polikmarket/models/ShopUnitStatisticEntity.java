package org.polik.polikmarket.models;

import lombok.*;
import org.polik.polikmarket.dto.imports.ShopUnitImport;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Polik on 6/21/2022
 */
@Entity
@Getter
@Setter
@Table(name = "shop_unit_statistic")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class ShopUnitStatisticEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "unit_id", updatable = false, nullable = false)
    private UUID unitId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "parent_id", updatable = false)
    private UUID parentId;

    @Column(name = "type", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private ShopUnitType type;

    @Column(name = "price")
    private Long price;

    @Column(name = "date", updatable = false, nullable = false)
    private LocalDateTime date;

    public ShopUnitStatisticEntity(ShopUnitImport unit, LocalDateTime date) {
        this.unitId = unit.getId();
        this.name = unit.getName();
        this.type = unit.getType();
        this.price = unit.getPrice();
        this.parentId = unit.getParentId();
        this.date = date;
    }

    public ShopUnitStatisticEntity(UUID id, UUID unitId, String name, UUID parentId, String type, BigDecimal price, LocalDateTime date) {
        this.id = id;
        this.unitId = unitId;
        this.name = name;
        this.parentId = parentId;
        this.type = ShopUnitType.valueOf(type);
        this.price = price.longValue();
        this.date = date;
    }
}
