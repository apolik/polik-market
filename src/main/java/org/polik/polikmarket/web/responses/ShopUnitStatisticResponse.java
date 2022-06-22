package org.polik.polikmarket.web.responses;

import org.polik.polikmarket.dto.units.ShopUnitStatisticUnit;

import java.util.Set;

/**
 * Created by Polik on 6/10/2022
 */
public record ShopUnitStatisticResponse(Set<ShopUnitStatisticUnit> items) {
}
