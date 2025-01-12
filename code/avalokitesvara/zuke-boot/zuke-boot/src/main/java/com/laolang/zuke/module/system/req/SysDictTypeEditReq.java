package com.laolang.zuke.module.system.req;

import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import lombok.Data;

@Data
public class SysDictTypeEditReq {
    private Long id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
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
}
