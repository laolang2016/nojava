package com.laolang.zuke.module.system.req;

import com.laolang.zuke.framework.common.domain.BasePageReq;
import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictTypeListReq extends BasePageReq {


    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     *
     * @see SysDictEnums.Type
     */
    private String type;

    /**
     * 状态
     *
     * @see CommonEntityEnums.Status
     */
    private String status;

    /**
     * @see SysDictEnums.GroupCode
     */
    private String groupCode;

    /**
     * 创建时间 - 开始
     */
    private Date startCreateTime;

    /**
     * 创建时间 - 结束
     */
    private Date endCreateTime;
}
