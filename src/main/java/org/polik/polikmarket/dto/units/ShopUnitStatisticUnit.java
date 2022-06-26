package org.polik.polikmarket.dto.units;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.polik.polikmarket.models.ShopUnitEntity;
import org.polik.polikmarket.models.ShopUnitStatisticEntity;
import org.polik.polikmarket.models.ShopUnitType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Polik on 6/15/2022
 */
@Data
@EqualsAndHashCode(of = "id")
@ToString
public class ShopUnitStatisticUnit {
    private UUID id;
    private String name;
    private UUID parentId;
    private ShopUnitType type;
    private Long price;
    private LocalDateTime date;

    public ShopUnitStatisticUnit(ShopUnitEntity u) {
        this.id = u.getId();
        this.name = u.getName();
        this.parentId = u.getParent() == null ? null : u.getParent().getId();
        this.type = u.getType();
        this.price = u.getPrice();
        this.date = u.getDate();
    }

    public ShopUnitStatisticUnit(ShopUnitStatisticEntity u) {
        this.id = u.getUnitId();
        this.name = u.getName();
        this.parentId = u.getParentId();
        this.type = u.getType();
        this.date = u.getDate();
        this.price = u.getPrice();
    }
}
