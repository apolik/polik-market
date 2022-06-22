package org.polik.polikmarket.models.shopunit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.polik.polikmarket.dto.imports.ShopUnitImport;

import javax.persistence.*;
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
}
