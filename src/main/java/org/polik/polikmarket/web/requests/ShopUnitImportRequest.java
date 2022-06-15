package org.polik.polikmarket.web.requests;

import org.polik.polikmarket.dto.imports.ShopUnitImport;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Polik on 6/10/2022
 */
public record ShopUnitImportRequest(@Valid List<ShopUnitImport> items,
                                    LocalDateTime updateDate) {
}
