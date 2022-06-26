package org.polik.polikmarket.web.controllers.shopunit;

import org.junit.jupiter.api.Test;
import org.polik.polikmarket.dto.units.ShopUnitStatisticUnit;
import org.polik.polikmarket.exceptions.NotFoundException;
import org.polik.polikmarket.services.ShopUnitService;
import org.polik.polikmarket.utils.JsonUtil;
import org.polik.polikmarket.web.controllers.AbstractControllerIT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.polik.polikmarket.ShopUnitTestData.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopUnitControllerIT extends AbstractControllerIT {
    @Autowired
    ShopUnitService service;

    static final String CREATE_REST_URL = "/imports";
    static final String GET_REST_URL = "/nodes/";
    static final String DELETE_REST_URL = "/delete/";
    static final String SALES_REST_URL = "/sales?date=";

    @Test
    void create() throws Exception {
        perform(MockMvcRequestBuilders.post(CREATE_REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isOk());
    }

    @Test
    void create_passInvalidUnit() throws Exception {
        perform(MockMvcRequestBuilders.post(CREATE_REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewInvalid())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.post(CREATE_REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andExpect(status().isOk());
    }

    @Test
    void update_passInvalidUnit() throws Exception {
        perform(MockMvcRequestBuilders.post(CREATE_REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdatedInvalid())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(GET_REST_URL + TVS_ID))
                .andExpect(status().isOk())
                .andExpect(SHOP_UNIT_MATCHER.contentJson(tvs));

        perform(MockMvcRequestBuilders.get(GET_REST_URL + SMARTPHONES_ID))
                .andExpect(status().isOk())
                .andExpect(SHOP_UNIT_MATCHER.contentJson(smartphones));

        perform(MockMvcRequestBuilders.get(GET_REST_URL + GOODS_ID))
                .andExpect(status().isOk())
                .andExpect(SHOP_UNIT_MATCHER.contentJson(goods));
    }

    @Test
    void get_passInvalidUUID() throws Exception {
        perform(MockMvcRequestBuilders.get(GET_REST_URL + INVALID_UUID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void get_getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(GET_REST_URL + NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(DELETE_REST_URL + TVS_ID))
                .andExpect(status().isOk());

        assertThrows(NotFoundException.class, () -> service.get(TVS_ID));
        assertThrows(NotFoundException.class, () -> service.get(SAMSON70_ID));
    }

    @Test
    void delete_deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(DELETE_REST_URL + NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSales() throws Exception {
        LocalDateTime date = smartphones.getDate();

        ResultActions actions = perform(MockMvcRequestBuilders.get(SALES_REST_URL + date))
                .andExpect(status().isOk());

        List<ShopUnitStatisticUnit> units = STATISTIC_RESPONSE_MATCHER.readFromJson(actions).items();

        assertEquals(sales(), units);
    }

    @Test
    void getSales_passInvalidDate() throws Exception {
        String invalidDate = "invalidDate";
        perform(MockMvcRequestBuilders.get(SALES_REST_URL + invalidDate))
                .andExpect(status().isBadRequest());
    }
}