package org.polik.polikmarket.dto.statistics;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.polik.polikmarket.models.shopunit.ShopUnitEntity;
import org.polik.polikmarket.models.shopunit.ShopUnitType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Polik on 6/15/2022
 */
@Data
@EqualsAndHashCode(of = "id")
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
}
