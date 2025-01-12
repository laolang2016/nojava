package com.laolang.zuke.module.system.req;

import com.laolang.zuke.framework.common.domain.BasePageReq;
import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictDataListReq extends BasePageReq {

    /**
     * 字典类型
     *
     * @see SysDictEnums.Type
     */
    private String type;

    /**
     * 字典所在分组
     *
     * @see SysDictEnums.GroupCode
     */
    private String groupCode;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典状态
     *
     * @see CommonEntityEnums.Status
     */
    private String status;
}
