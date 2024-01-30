package io.web.app.file.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 加解密注解，标注在属性上
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
// 使用自定义的序列化实现
@JsonSerialize(using = FileJsonFiledSerializer.class)
// 使用自定义的序列化实现
@JsonDeserialize(using = FileJsonFiledDeSerializer.class)
public @interface JsonFile {

}