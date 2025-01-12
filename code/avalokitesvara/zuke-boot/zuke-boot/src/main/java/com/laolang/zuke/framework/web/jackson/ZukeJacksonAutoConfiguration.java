package com.laolang.zuke.framework.web.jackson;
import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZukeJacksonAutoConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            /* 数字格式化 */
            // BigDecimal 格式化
            builder.serializerByType(BigDecimal.class, new JsonSerializer<BigDecimal>() {
                @Override
                public void serialize(BigDecimal value, JsonGenerator gen,
                                      SerializerProvider serializers)
                        throws IOException {
                    DecimalFormat format = new DecimalFormat("0.00");
                    gen.writeString(format.format(value));
                }
            });
            // Long 格式化
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(long.class, ToStringSerializer.instance);

            /* 时间格式化 */
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(
                    DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            builder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(
                            DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            builder.serializerByType(LocalTime.class,
                    new LocalTimeSerializer(
                            DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(
                    DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            builder.deserializerByType(LocalDate.class,
                    new LocalDateDeserializer(
                            DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            builder.deserializerByType(LocalTime.class,
                    new LocalTimeDeserializer(
                            DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        };
    }
}
