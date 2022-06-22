package org.polik.polikmarket.web.controllers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.polik.polikmarket.dto.units.ShopUnit;
import org.polik.polikmarket.services.ShopUnitService;
import org.polik.polikmarket.web.requests.ShopUnitImportRequest;
import org.polik.polikmarket.web.responses.ShopUnitStatisticResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
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

    @SneakyThrows
    @GetMapping("/nodes/{id}")
    public ShopUnit get(@PathVariable UUID id) {
        return service.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/sales")
    public ShopUnitStatisticResponse getSales(
            @RequestParam @DateTimeFormat(iso = DATE_TIME) LocalDateTime date) {
        return service.getOffersBetween(date.minusDays(1), date);
    }

    @GetMapping("/node/{id}/statistic")
    public ShopUnitStatisticResponse getStatistic(@PathVariable UUID id,
                                                  @RequestParam @Nullable @DateTimeFormat(iso = DATE_TIME) LocalDateTime dateStart,
                                                  @RequestParam @Nullable @DateTimeFormat(iso = DATE_TIME) LocalDateTime dateEnd) {


        return service.getStatisticBetween(id, dateStart, dateEnd);
    }
}
