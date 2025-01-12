package com.laolang.zuke.module.system.req;

import com.laolang.zuke.framework.common.enums.CommonEnums;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysDictTypeChangeStatusReq {

    @NotNull(message = "id 必填")
    private Long id;
    @NotNull(message = "状态必填")
    private String status;
    /**
     * 是否更新字典数据
     *
     * @see CommonEnums.YesNoChar
     */
    private String updateData;
}
