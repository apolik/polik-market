package org.polik.polikmarket;

import lombok.experimental.UtilityClass;
import org.polik.polikmarket.dto.imports.ShopUnitImport;
import org.polik.polikmarket.dto.units.ShopUnit;
import org.polik.polikmarket.dto.units.ShopUnitStatisticUnit;
import org.polik.polikmarket.web.requests.ShopUnitImportRequest;
import org.polik.polikmarket.web.responses.ShopUnitStatisticResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.polik.polikmarket.models.ShopUnitType.CATEGORY;
import static org.polik.polikmarket.models.ShopUnitType.OFFER;

/**
 * Created by Polik on 6/17/2022
 */
@UtilityClass
public class ShopUnitTestData {
    public static final MatcherFactory.Matcher<ShopUnit> SHOP_UNIT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ShopUnit.class);
    public static final MatcherFactory.Matcher<ShopUnitStatisticResponse> STATISTIC_RESPONSE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ShopUnitStatisticResponse.class);

    public static final ShopUnit goods;
    public static final ShopUnit smartphones;
    public static final ShopUnit tvs;

    public static final ShopUnit jphone13;
    public static final ShopUnit xomia10;

    public static final ShopUnit samson70;
    public static final ShopUnit phyllis50;
    public static final ShopUnit goldstar65;

    public static final UUID GOODS_ID = UUID.fromString("069cb8d7-bbdd-47d3-ad8f-82ef4c269df1");
    public static final UUID SMARTPHONES_ID = UUID.fromString("d515e43f-f3f6-4471-bb77-6b455017a2d2");
    public static final UUID TVS_ID = UUID.fromString("1cc0129a-2bfe-474c-9ee6-d435bf5fc8f2");

    public static final UUID SAMSON70_ID = UUID.fromString("98883e8f-0507-482f-bce2-2fb306cf6483");

    public static final UUID NOT_FOUND_ID = UUID.randomUUID();
    public static final String INVALID_UUID = "9t8n3m8f-0507-482i-bce2-2fo306pw6483";

    public static ShopUnitImportRequest getNew() {
        return new ShopUnitImportRequest(
                List.of(new ShopUnitImport(UUID.randomUUID(), "новая категория", null, CATEGORY, null)),
                LocalDateTime.now()
        );
    }

    public static ShopUnitImportRequest getNewInvalid() {
        return new ShopUnitImportRequest(
                List.of(new ShopUnitImport(UUID.randomUUID(), "Invalid", null, CATEGORY, 777L)),
                LocalDateTime.now()
        );
    }

    public static ShopUnitImportRequest getUpdatedInvalid() {
        return new ShopUnitImportRequest(
                List.of(new ShopUnitImport(jphone13.getId(), jphone13.getName(), jphone13.getParentId(), CATEGORY, jphone13.getPrice())),
                LocalDateTime.now()
        );
    }

    public static ShopUnitImportRequest getUpdated() {
        return new ShopUnitImportRequest(
                List.of(new ShopUnitImport(xomia10.getId(), "обновленный супер-пупер-дупер xomia 10", xomia10.getParentId(), xomia10.getType(), 777L)),
                LocalDateTime.now()
        );
    }

    public static List<ShopUnitStatisticUnit> sales() {
        return Stream.of(jphone13, xomia10)
                .map(ShopUnitStatisticUnit::new)
                .toList();
    }

    static {
        samson70 = ShopUnit.builder()
                .id(SAMSON70_ID)
                .name("Samson 70\" LED UHD Smart")
                .price(32999L)
                .date(LocalDateTime.parse("2022-02-03T12:00:00.00"))
                .type(OFFER)
                .parentId(TVS_ID)
                .build();

        phyllis50 = ShopUnit.builder()
                .id(UUID.fromString("74b81fda-9cdc-4b63-8927-c978afed5cf4"))
                .name("Phyllis 50\" LED UHD Smarter")
                .price(49999L)
                .date(LocalDateTime.parse("2022-02-03T12:00:00.00"))
                .type(OFFER)
                .parentId(TVS_ID)
                .build();

        goldstar65 = ShopUnit.builder()
                .id(UUID.fromString("73bc3b36-02d1-4245-ab35-3106c9ee1c65"))
                .name("Goldstar 65\" LED UHD LOL Very Smart")
                .price(69999L)
                .date(LocalDateTime.parse("2022-02-03T15:00:00.00"))
                .type(OFFER)
                .parentId(TVS_ID)
                .build();

        jphone13 = ShopUnit.builder()
                .id(UUID.fromString("863e1a7a-1304-42ae-943b-179184c077e3"))
                .name("jPhone 13")
                .price(79999L)
                .date(LocalDateTime.parse("2022-02-02T12:00:00.00"))
                .type(OFFER)
                .parentId(SMARTPHONES_ID)
                .build();

        xomia10 = ShopUnit.builder()
                .id(UUID.fromString("b1d8fd7d-2ae3-47d5-b2f9-0f094af800d4"))
                .name("Xomiа Readme 10")
                .price(59999L)
                .date(LocalDateTime.parse("2022-02-02T12:00:00.00"))
                .type(OFFER)
                .parentId(SMARTPHONES_ID)
                .build();

        tvs = ShopUnit.builder()
                .id(TVS_ID)
                .name("Телевизоры")
                .price(50999L)
                .date(LocalDateTime.parse("2022-02-03T15:00:00.00"))
                .parentId(GOODS_ID)
                .type(CATEGORY)
                .children(Set.of(samson70, phyllis50, goldstar65))
                .build();

        smartphones = ShopUnit.builder()
                .id(SMARTPHONES_ID)
                .name("Смартфоны")
                .price(69999L)
                .date(LocalDateTime.parse("2022-02-02T12:00:00.00"))
                .parentId(GOODS_ID)
                .type(CATEGORY)
                .children(Set.of(jphone13, xomia10))
                .build();

        goods = ShopUnit.builder()
                .id(GOODS_ID)
                .name("Товары")
                .price(58599L)
                .date(LocalDateTime.parse("2022-02-03T15:00:00.00"))
                .type(CATEGORY)
                .children(Set.of(tvs, smartphones))
                .build();
    }
}
