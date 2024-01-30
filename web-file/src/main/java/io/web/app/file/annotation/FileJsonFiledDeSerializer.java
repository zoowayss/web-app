package io.web.app.file.annotation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 * 序列化注解自定义实现
 * JsonDeserializer<String>：指定String类型，deserialize()方法用于将修改后的数据载入
 * Jackson使用ContextualSerializer在序列化时获取字段注解的属性
 * </pre>
 */

public class FileJsonFiledDeSerializer extends JsonDeserializer<Object> implements ContextualDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        if (p.getCurrentToken().id() == JsonTokenId.ID_START_ARRAY) {

            List<String> list = p.readValueAs(new TypeReference<List<String>>() {
            });
            return list.stream().map(s -> s.substring(s.lastIndexOf("/") + 1)).toList();
        }
        String valueAsString = p.getValueAsString();
        // 数据不为空时解密数据
        if (StringUtils.hasText(valueAsString)) {
            // EncryUtil为封装的加解密工具
            String[] split = valueAsString.split("/");
            return split[split.length - 1];
        }
        return valueAsString;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JsonFile annotation = property.getAnnotation(JsonFile.class);
        // 只针对String类型
        if (Objects.nonNull(annotation)) {
            return this;
        }
        return ctxt.findContextualValueDeserializer(property.getType(), property);
    }
}