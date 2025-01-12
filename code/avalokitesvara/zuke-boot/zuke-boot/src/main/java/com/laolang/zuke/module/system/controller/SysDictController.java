package com.laolang.zuke.module.system.controller;

import com.laolang.zuke.module.system.logic.SysDictLogic;
import com.laolang.zuke.module.system.req.SysDictTypeListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeListReq;
import com.laolang.zuke.persist.system.entity.SysDictType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("admin/system/dict")
@RestController
public class SysDictController {

    private final SysDictLogic sysDictLogic;

    @PostMapping("type/list")
    public SysDictTypeListRsp typeList(@RequestBody SysDictTypeListReq req) {
        return sysDictLogic.typeList(req);
    }
}
