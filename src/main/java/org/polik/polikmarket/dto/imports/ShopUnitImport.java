package org.polik.polikmarket.dto.imports;

import lombok.Data;
import org.polik.polikmarket.models.shopunit.ShopUnitType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Created by Polik on 6/10/2022
 */

// при обновлении товара/категории обновленными считаются **все** их параметры
// при обновлении параметров элемента обязательно обновляется поле **date** в соответствии с временем обновления
// Товары/категории импортированные повторно обновляют текущие.
@Data
public class ShopUnitImport {
    @NotNull
    private UUID id; // в одном запросе не может быть двух элементов с одинаковым id
    @NotNull
    private String name; // название элемента не может быть null
    private UUID parentId; // 1. родителем товара или категории может быть только категория
                            // 2. принадлежность к категории определяется полем parentId
                            // 3. товар или категория могут не иметь родителя
    @NotNull
    private ShopUnitType type; // Изменение типа элемента с товара на категорию или с категории на товар не допускается
    @Min(0)
    private Long price; /* 1. у категорий поле price должно содержать null
                                2. цена товара не может быть null и должна быть больше либо равна нулю.
                            */
}
