package org.polik.polikmarket.utils;

import lombok.experimental.UtilityClass;
import org.polik.polikmarket.dto.imports.ShopUnitImport;
import org.polik.polikmarket.dto.units.ShopUnit;
import org.polik.polikmarket.dto.units.ShopUnitStatisticUnit;
import org.polik.polikmarket.exceptions.NotFoundException;
import org.polik.polikmarket.models.ShopUnitEntity;
import org.polik.polikmarket.models.ShopUnitStatisticEntity;
import org.polik.polikmarket.models.ShopUnitType;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Polik on 6/10/2022
 */
@UtilityClass
public class ShopUnitUtil {
    public static ShopUnitEntity fromImport(ShopUnitImport unitImport, LocalDateTime date, ShopUnitEntity parent) {
        return ShopUnitEntity.builder()
                .id(unitImport.getId())
                .name(unitImport.getName())
                .parent(parent)
                .price(unitImport.getPrice())
                .type(unitImport.getType())
                .date(date)
                .build();
    }

    public static List<ShopUnitStatisticUnit> toStatisticUnits(Set<ShopUnitEntity> units) {
        return units.stream()
                .map(ShopUnitStatisticUnit::new)
                .toList();
    }

    public static ShopUnit getOrThrowNotFound(Optional<ShopUnit> shopUnit, UUID id) {
        return shopUnit.orElseThrow(
                () -> new NotFoundException(
                        String.format("No such shop unit for id: %s", id)
                )
        );
    }

    public static ShopUnit setChildren(Collection<ShopUnit> units, UUID id) {
        Map<UUID, ShopUnit> map = units.stream().collect(
                Collectors.toMap(ShopUnit::getId, unit -> unit)
        );

        units.forEach(unit -> {
            ShopUnit unit1 = map.get(unit.getParentId());
            if (unit1 != null) {
                unit1.addChildren(unit);
            }
        });

        Optional<ShopUnit> result = Optional.ofNullable(map.get(id));

        return getOrThrowNotFound(result, id);
    }

    public static List<ShopUnitStatisticUnit> toStatisticUnits2(Collection<ShopUnitStatisticEntity> entities) {
        return entities.stream()
                .map(ShopUnitStatisticUnit::new)
                .toList();
    }

    public static ShopUnitEntity getOrThrowValidationException(Optional<ShopUnitEntity> shopUnit,
                                                               UUID id, ShopUnitType type) {
        return shopUnit.orElseThrow(
                () -> new ValidationException(
                        String.format("No such shop unit for id: %s and type: %s", id, type.name())
                )
        );
    }
}
