package com.laolang.zuke.framework.mybatis.enums;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommonEntityEnums {

    @Getter
    @AllArgsConstructor
    public enum DefaultValue {
        YES("Y", "默认值"),
        NO("N", "非默认值");
        private final String value;
        private final String desc;

        private static final Map<String, DefaultValue> value_map = Maps.newHashMap();

        static {
            for (DefaultValue e : values()) {
                value_map.put(e.getValue(), e);
            }
        }

        public static boolean hasValue(String value) {
            return value_map.containsKey(value);
        }

        public static boolean yes(String value) {
            return hasValue(value) && StrUtil.equals(YES.getValue(), value);
        }

        public static DefaultValue getByValue(String value) {
            return value_map.get(value);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Status {
        NORMAL("0", "正常"),
        STOP("1", "停用");
        private final String value;
        private final String desc;

        private static final Map<String, Status> value_map = Maps.newHashMap();

        static {
            for (Status e : values()) {
                value_map.put(e.getValue(), e);
            }
        }

        public static Status getByValue(String value) {
            return value_map.get(value);
        }

        public static boolean hasValue(String value) {
            return value_map.containsKey(value);
        }
    }
}
