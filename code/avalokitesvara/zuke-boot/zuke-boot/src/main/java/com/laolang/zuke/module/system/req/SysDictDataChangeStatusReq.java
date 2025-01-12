package com.laolang.zuke.module.system.req;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysDictDataChangeStatusReq {
    @NotNull(message = "id 必填")
    private Long id;
    @NotNull(message = "状态必填")
    private String status;
}
