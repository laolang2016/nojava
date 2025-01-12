package com.laolang.zuke;

import com.yomahub.tlog.core.enhance.bytes.AspectLogEnhance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ZukeApplication {

    static {
        AspectLogEnhance.enhance();
    }

    public static void main(String[] args) {
        SpringApplication.run(ZukeApplication.class, args);
        log.info("zuke is running...");
    }
}
