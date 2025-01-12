package com.laolang.zuke.module.system.rsp;

import com.laolang.zuke.framework.common.domain.BasePageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictTypeListReq extends BasePageReq {

    private String name;
}
