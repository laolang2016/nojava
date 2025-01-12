package com.laolang.zuke.framework.common.enums;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonEnums {
    @Getter
    @AllArgsConstructor
    public enum YesNoChar {
        YES("Y", "是"),
        NO("N", "否");
        private final String value;
        private final String desc;

        private static final Map<String, YesNoChar> value_map = Maps.newHashMap();

        static {
            for (YesNoChar e : values()) {
                value_map.put(e.getValue(), e);
            }
        }

        public static YesNoChar getByValue(String value) {
            return value_map.get(value);
        }

        public static boolean hasValue(String value) {
            return value_map.containsKey(value);
        }

        public static boolean yes(String value) {
            return hasValue(value) && StrUtil.equals(YES.getValue(), value);
        }

        public static boolean no(String value) {
            return hasValue(value) && StrUtil.equals(NO.getValue(), value);
        }
    }
}
