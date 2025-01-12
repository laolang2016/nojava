package com.laolang.zuke.persist.system.enums;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SysDictEnums {
    @Getter
    @AllArgsConstructor
    public enum GroupCode {
        SYSTEM("system", "系统");
        private final String value;
        private final String desc;

        public static final Map<String, GroupCode> value_map = Maps.newHashMap();

        static {
            for (GroupCode e : values()) {
                value_map.put(e.getValue(), e);
            }
        }

        public static GroupCode getByValue(String value) {
            return value_map.get(value);
        }

        public static String getDescByValue(String value) {
            GroupCode groupCode = getByValue(value);
            return Objects.isNull(groupCode) ? value : groupCode.getDesc();
        }

        public static String getDescByValue(String value, String defaultDesc) {
            GroupCode groupCode = getByValue(value);
            return Objects.isNull(groupCode) ? defaultDesc : groupCode.getDesc();
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        /**
         *
         */
        GENDER("gender", "用户性别"),
        MODULE("module", "模块");
        private final String value;
        private final String desc;

        private static final Map<String, Type> value_map = Maps.newHashMap();

        static {
            for (Type e : values()) {
                value_map.put(e.getValue(), e);
            }
        }

        public static Type getByValue(String value) {
            return value_map.get(value);
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Module {
        SYSTEM("system", "系统"),
        TENANT("tenant", "租客"),
        LANDLORD("landlord", "房东"),
        AGENT("agent", "中介");

        private final String value;
        private final String desc;

        private static final Map<String, Module> value_map = Maps.newHashMap();

        static {
            for (Module e : values()) {
                value_map.put(e.getValue(), e);
            }
        }

        public static Module getByValue(String value) {
            return value_map.get(value);
        }
    }
}
