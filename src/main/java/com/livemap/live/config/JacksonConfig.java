package com.livemap.live.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.livemap.live.utils.TimeUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * Jackson config
 */
@Configuration
public class JacksonConfig {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Module jsonMapperJava8DateTimeModule() {
        SimpleModule bean = new SimpleModule();

        java.time.format.DateTimeFormatter utc =
                java.time.format.DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(TimeUtils.mskZone());

        bean.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {

            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException {
                return LocalDateTime.parse(jsonParser.getValueAsString(), utc);
            }
        });

        bean.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {

            @Override
            public void serialize(
                    LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException {
                jsonGenerator.writeString(utc.format(localDateTime));
            }
        });

        return bean;
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone(TimeUtils.mskZone()));
        builder.indentOutput(true)
                .dateFormat(dateFormat)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
                .modules(jsonMapperJava8DateTimeModule())
                .serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder.build();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeDefaultObjectMapper() {
        return builder -> {
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            builder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        };
    }
}
