package com.ayundao.base.utils;

import com.ayundao.base.BaseEntity;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import com.alibaba.fastjson.*;
import com.ayundao.base.Page;
import org.springframework.util.Assert;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: JsonUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/21 14:25
 * @Description: Json工具类
 * @Version: V1.0
 */
public class JsonUtils {

    /**
     * ObjectMapper
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 不可实例化
     */
    private JsonUtils() {
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param value
     *            对象
     * @return JSON字符串
     */
    public static String toJson(Object value) {
        Assert.notNull(value, "");

        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将Entity转化为Json字符串
     * @param obj
     * @return
     * @author 念
     */
    public static com.alibaba.fastjson.JSONObject getJson(Object obj) {
        Map<String, Field> map = ClassUtils.getDeclaredFieldsWithSuper(obj.getClass());
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        for (Map.Entry<String, Field> entry : map.entrySet()) {
            String key = entry.getKey();
            Field field = entry.getValue();
            Class cls = field.getType();
            if (!BaseEntity.class.isAssignableFrom(cls) && !Collection.class.isAssignableFrom(cls)
                    && !Map.class.isAssignableFrom(cls)) {
                json.put(key, ClassUtils.forceGetProperty(obj, field.getName()));
            }
        }
        return json;
    }

    /**
     * 反向父子关系
     * @param json
     */
    private static void reverseFather(com.alibaba.fastjson.JSONObject json) {
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            String key = entry.getKey();
            if (key.equals("father") && entry.getValue() != null) {
                com.alibaba.fastjson.JSONObject sonJson = new com.alibaba.fastjson.JSONObject((Map<String, Object>) entry.getValue());
                for (Map.Entry<String, Object> father : json.entrySet()) {
                    String fatherKey = father.getKey();
                    com.alibaba.fastjson.JSONObject fahterJson = new com.alibaba.fastjson.JSONObject((Map<String, Object>) father.getValue());
                    if (fahterJson.get("id").equals(sonJson.get("id"))) {

                    } 
                }
                json.remove(key);
            }
        }
    }

    public static com.alibaba.fastjson.JSONObject getPage(Page<?> page) {
        //todo 需要再次封装,需要更加适合封装page
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        com.alibaba.fastjson.JSONArray arr = new com.alibaba.fastjson.JSONArray();
        for (Object o : page.getContent()) {
            arr.add(getJson(o));
        }
        jsonObject.put("total", page.getTotal());
        jsonObject.put("page", page.getPageNumber());
        jsonObject.put("content", arr);
        return jsonObject;
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json
     *            JSON字符串
     * @param valueType
     *            类型
     * @return 对象
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json, "");
        Assert.notNull(valueType, "");

        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json
     *            JSON字符串
     * @param typeReference
     *            类型
     * @return 对象
     */
    public static <T> T toObject(String json, TypeReference<?> typeReference) {
        Assert.hasText(json, "");
        Assert.notNull(typeReference, "");

        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param json
     *            JSON字符串
     * @param javaType
     *            类型
     * @return 对象
     */
    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json, "");
        Assert.notNull(javaType , "");

        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (JsonParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将JSON字符串转换为树
     *
     * @param json
     *            JSON字符串
     * @return 树
     */
    public static JsonNode toTree(String json) {
        Assert.hasText(json, "");

        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换为JSON流
     *
     * @param writer
     *            Writer
     * @param value
     *            对象
     */
    public static void writeValue(Writer writer, Object value) {
        try {
            OBJECT_MAPPER.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 构造类型
     *
     * @param type
     *            类型
     * @return 类型
     */
    public static JavaType constructType(Type type) {
        Assert.notNull(type, "");
        return TypeFactory.defaultInstance().constructType(type);
    }

    /**
     * 构造类型
     *
     * @param typeReference
     *            类型
     * @return 类型
     */
    public static JavaType constructType(TypeReference<?> typeReference) {
        Assert.notNull(typeReference, "");
        return TypeFactory.defaultInstance().constructType(typeReference);
    }
}
