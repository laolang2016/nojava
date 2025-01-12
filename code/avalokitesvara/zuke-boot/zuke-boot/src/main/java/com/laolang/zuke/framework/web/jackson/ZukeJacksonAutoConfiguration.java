package com.laolang.zuke.framework.web.jackson;

import com.laolang.zuke.framework.web.jackson.module.BigDecimalModule;
import com.laolang.zuke.framework.web.jackson.module.Jdk8TimeModule;
import com.laolang.zuke.framework.web.jackson.module.LongModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZukeJacksonAutoConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            builder.modulesToInstall(new BigDecimalModule(), new LongModule(), new Jdk8TimeModule());
        };
    }
}
