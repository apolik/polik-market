package org.polik.polikmarket.web.controllers;

import lombok.AllArgsConstructor;
import org.polik.polikmarket.models.shopunit.ShopUnit;
import org.polik.polikmarket.services.ShopUnitService;
import org.polik.polikmarket.web.requests.ShopUnitImportRequest;
import org.polik.polikmarket.web.responses.ShopUnitStatisticResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

/**
 * Created by Polik on 6/10/2022
 */
@RestController
@AllArgsConstructor
public class ShopUnitController {
    private final ShopUnitService service;

    @PostMapping("/imports")
    public void handleImport(
            @RequestBody @Valid ShopUnitImportRequest request) {
        service.handleImport(request);
    }

    @GetMapping("/nodes/{id}")
    public ShopUnit get(@PathVariable UUID id) {
        return service.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/sales")
    public ShopUnitStatisticResponse getOffersBetween(
            @RequestParam @DateTimeFormat(iso = DATE_TIME) LocalDateTime date) {
        return service.getOffersBetween(date.minusDays(1), date);
    }
}
