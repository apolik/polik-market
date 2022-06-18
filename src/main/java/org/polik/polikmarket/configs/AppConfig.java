package org.polik.polikmarket.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.polik.polikmarket.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Polik on 6/17/2022
 */
@Configuration
public class AppConfig {
    @Autowired
    public void storeObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }
}
