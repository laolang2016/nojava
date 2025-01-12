package com.laolang.zuke.module.system.req;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SysDictDataDeleteReq {

    @NotNull(message = "字典 id 列表不能为空")
    private List<Long> ids;
}
