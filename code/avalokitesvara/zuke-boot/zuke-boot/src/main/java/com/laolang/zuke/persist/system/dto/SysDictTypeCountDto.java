package com.laolang.zuke.persist.system.dto;

import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import lombok.Data;

@Data
public class SysDictTypeCountDto {
    /**
     * 字典名称
     */
    private String name;

    /**
     * zidian 类型
     *
     * @see SysDictEnums.Type
     */
    private String type;

    /**
     * @see SysDictEnums.GroupCode
     */
    private String groupCode;

    /**
     * @see CommonEntityEnums.Status
     */
    private String status;

    /**
     * 生效中的字典值数量
     */
    private Integer enableCount;
    /**
     * 禁用中的字典值数量
     */
    private Integer disabledcount;

    public boolean hasEnabled() {
        return enableCount > 0;
    }
}
