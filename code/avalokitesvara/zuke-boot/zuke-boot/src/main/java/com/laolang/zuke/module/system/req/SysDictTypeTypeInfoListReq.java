package com.laolang.zuke.module.system.req;

import com.laolang.zuke.persist.system.enums.SysDictEnums;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysDictTypeTypeInfoListReq {

    /**
     * 字典分组
     *
     * @see SysDictEnums.GroupCode
     */
    @NotBlank(message = "字典分组编码不能为空")
    private String groupCode;
}
