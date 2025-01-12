package com.laolang.zuke;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.laolang.zuke.framework.web.jackson.module.BigDecimalModule;
import com.laolang.zuke.framework.web.jackson.module.Jdk8TimeModule;
import com.laolang.zuke.framework.web.jackson.module.LongModule;
import com.laolang.zuke.persist.system.entity.SysDictType;
import java.util.Date;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

public class CommonTest {

    @SneakyThrows
    @Test
    public void testOne(){
        SysDictType type = new SysDictType();
        type.setId(1001L);
        type.setCreateTime(new Date());

        ObjectMapper om = new ObjectMapper();
        om.registerModules(new BigDecimalModule(), new LongModule(), new Jdk8TimeModule());
        om.enable(SerializationFeature.INDENT_OUTPUT);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter().withObjectIndenter(new DefaultIndenter("    ", "\n"));


        System.out.println(om.writer(printer).writeValueAsString(type));
    }
}
