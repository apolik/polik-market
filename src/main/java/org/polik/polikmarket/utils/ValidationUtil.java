package org.polik.polikmarket.utils;

import lombok.experimental.UtilityClass;
import org.polik.polikmarket.dto.imports.ShopUnitImport;
import org.polik.polikmarket.exceptions.GlobalExceptionHandler;
import org.polik.polikmarket.exceptions.NotFoundException;
import org.polik.polikmarket.models.shopunit.ShopUnitType;

import javax.validation.ValidationException;

/**
 * Created by Polik on 6/12/2022
 */
@UtilityClass
public class ValidationUtil {
    public static void rejectIfInvalidType(ShopUnitImport unit) {
        if (unit.getType() == ShopUnitType.CATEGORY && unit.getPrice() != null
                || unit.getType() == ShopUnitType.OFFER && unit.getPrice() == null) {
            throw new ValidationException(GlobalExceptionHandler.EXCEPTION_INVALID_TYPE);
        }
    }

    public static void checkModification(int count, String message) {
        if (count == 0) {
            throw new NotFoundException(message);
        }
    }
}
