package com.laolang.zuke.module.system.rsp;

import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import java.util.Date;
import lombok.Data;

@Data
public class SysDictTypeListRsp {

    private Long id;
    private String name;

    /**
     * 字典类型
     *
     * @see SysDictEnums.Type
     */
    private String type;

    /**
     * 字典分组名称
     */
    private String groupName;

    /**
     * 字典分组
     *
     * @see SysDictEnums.GroupCode
     */
    private String groupCode;

    /**
     * 字典类型状态
     *
     * @see CommonEntityEnums.Status
     */
    private String status;
    private String remark;
    private Date createTime;
}
