package org.polik.polikmarket.web.controllers.shopunit;

import org.junit.jupiter.api.Test;
import org.polik.polikmarket.exceptions.NotFoundException;
import org.polik.polikmarket.services.ShopUnitService;
import org.polik.polikmarket.utils.JsonUtil;
import org.polik.polikmarket.web.controllers.AbstractControllerIT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.polik.polikmarket.web.controllers.shopunit.ShopUnitTestData.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopUnitControllerIT extends AbstractControllerIT {
    @Autowired
    ShopUnitService service;

    static final String CREATE_REST_URL = "/imports";
    static final String GET_REST_URL = "/nodes/";
    static final String DELETE_REST_URL = "/delete/";

    @Test
    void create() throws Exception {
        perform(MockMvcRequestBuilders.post(CREATE_REST_URL)
                .contentType(APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isOk());
    }

    @Test
    void createInvalid() throws Exception {
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
    void updateInvalid() throws Exception {
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
    void passInvalidUUID() throws Exception {
        perform(MockMvcRequestBuilders.get(GET_REST_URL + INVALID_UUID))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(GET_REST_URL + NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(DELETE_REST_URL + TVS_ID))
                .andExpect(status().isOk());

        assertThrows(NotFoundException.class, () -> service.get(SAMSON70_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(DELETE_REST_URL + NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }
}