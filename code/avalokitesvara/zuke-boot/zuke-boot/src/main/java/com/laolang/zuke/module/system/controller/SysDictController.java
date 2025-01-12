package com.laolang.zuke.module.system.controller;

import com.laolang.zuke.framework.common.domain.R;
import com.laolang.zuke.module.system.logic.SysDictLogic;
import com.laolang.zuke.module.system.req.SysDictTypeListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeListReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("admin/system/dict")
@RestController
public class SysDictController {

    private final SysDictLogic sysDictLogic;

    @PostMapping("type/list")
    public R<SysDictTypeListRsp> typeList(@RequestBody SysDictTypeListReq req) {
        return R.ok(sysDictLogic.typeList(req));
    }
}
