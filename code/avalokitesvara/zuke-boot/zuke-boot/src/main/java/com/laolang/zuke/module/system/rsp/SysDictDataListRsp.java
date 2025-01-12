package com.laolang.zuke.module.system.rsp;

import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import java.util.Date;
import lombok.Data;

@Data
public class SysDictDataListRsp {
    private Long id;
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
     * 是否默认值
     *
     * @see CommonEntityEnums.DefaultValue
     */
    private String defaultValue;

    /**
     * 字典状态
     *
     * @see CommonEntityEnums.Status
     */
    private String status;

    private String remark;

    private Date createTime;
}
