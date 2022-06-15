package org.polik.polikmarket.services;

import org.polik.polikmarket.models.shopunit.ShopUnit;
import org.polik.polikmarket.web.requests.ShopUnitImportRequest;
import org.polik.polikmarket.web.responses.ShopUnitStatisticResponse;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Polik on 2/1/2022
 */
public interface ShopUnitService {
    void handleImport(ShopUnitImportRequest request);

    ShopUnit get(UUID id);

    void delete(UUID id);

    ShopUnitStatisticResponse getOffersBetween(LocalDateTime startDate, LocalDateTime endDate);
}
