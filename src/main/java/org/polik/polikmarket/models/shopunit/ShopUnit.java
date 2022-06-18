package org.polik.polikmarket.models.shopunit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Polik on 6/13/2022
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
public class ShopUnit {
    private UUID id;
    private String name;
    private UUID parentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime date;
    private ShopUnitType type;
    private Long price;
    private Set<ShopUnit> children;

    public ShopUnit(UUID id, String name, UUID parentId, LocalDateTime date, String type, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.date = date;
        this.type = ShopUnitType.valueOf(type);
        this.price = price == null ? null : price.longValue();
    }

    @JsonIgnore
    public void addChildren(ShopUnit unit) {
        if (this.children == null) {
            this.children = new HashSet<>();
        }

        this.children.add(unit);
    }
}
