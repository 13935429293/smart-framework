package org.xiaoyang.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJson(T obj) {
        String json;

        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("POJO to JSON failure.", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        T pojo;

        try {
            pojo = OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOGGER.error("JSON to POJO failure.", e);
            throw new RuntimeException(e);
        }

        return pojo;
    }
}
