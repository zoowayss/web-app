package io.web.app.file.annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * <pre>
 * 序列化注解自定义实现
 * JsonSerializer<String>：指定String类型，serialize()方法用于将修改后的数据载入
 * Jackson使用ContextualSerializer在序列化时获取字段注解的属性
 * </pre>
 */

public class FileJsonFiledSerializer extends JsonSerializer<Object> implements ContextualSerializer {


    @Value("${web.file.base-url:http://localhost:8888}")
    private String fileBaseUrl;

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 数据不为空时加密数据

        if (value instanceof Collection<?>) {
            Collection<String> collection = (Collection<String>) value;
            gen.writeObject(collection.stream().map(s ->fileBaseUrl + s).toList());
            return;
        }
        if (value instanceof String) {
            String encrypt = fileBaseUrl + value;
            gen.writeString(encrypt);
            return;
        }
        gen.writeObject(value);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        JsonFile annotation = property.getAnnotation(JsonFile.class);
        return Objects.nonNull(annotation) ? this : prov.findValueSerializer(property.getType(), property);
    }

}