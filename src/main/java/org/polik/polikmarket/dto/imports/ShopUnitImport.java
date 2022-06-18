package org.polik.polikmarket.dto.imports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.polik.polikmarket.models.shopunit.ShopUnitType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Polik on 6/10/2022
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class ShopUnitImport {
    @NotNull
    private UUID id; // в одном запросе не может быть двух элементов с одинаковым id
    @NotNull
    private String name;
    private UUID parentId;
    @NotNull
    private ShopUnitType type;
    @Min(0)
    private Long price;
}
