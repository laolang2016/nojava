package com.laolang.zuke.framework.common.util;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.laolang.zuke.framework.web.jackson.module.BigDecimalModule;
import com.laolang.zuke.framework.web.jackson.module.Jdk8TimeModule;
import com.laolang.zuke.framework.web.jackson.module.LongModule;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtil {

    private static ObjectMapper om = new ObjectMapper();
    private static ObjectMapper omPretty = new ObjectMapper();
    private static final DefaultPrettyPrinter printer = new DefaultPrettyPrinter().withObjectIndenter(new DefaultIndenter("    ", "\n"));

    static {
        om.registerModules(new BigDecimalModule(), new LongModule(), new Jdk8TimeModule());

        omPretty.registerModules(new BigDecimalModule(), new LongModule(), new Jdk8TimeModule());
        omPretty.enable(SerializationFeature.INDENT_OUTPUT);

    }

    @SneakyThrows
    public static String toJsonString(Object o) {
        return toJsonString(o, false);
    }

    @SneakyThrows
    public static String toJsonString(Object o, boolean pretty) {
        if (pretty) {
            return omPretty.writer(printer).writeValueAsString(o);
        }
        return om.writeValueAsString(o);
    }


}
