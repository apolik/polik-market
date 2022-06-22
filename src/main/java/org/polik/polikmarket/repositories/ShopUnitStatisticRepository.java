package org.polik.polikmarket.repositories;

import org.polik.polikmarket.models.shopunit.ShopUnitStatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by Polik on 2/1/2022
 */
public interface ShopUnitStatisticRepository extends JpaRepository<ShopUnitStatisticEntity, UUID> {
}
