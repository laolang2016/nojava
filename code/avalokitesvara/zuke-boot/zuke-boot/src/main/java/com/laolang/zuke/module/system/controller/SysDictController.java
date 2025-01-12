package com.laolang.zuke.module.system.controller;

import com.laolang.zuke.framework.common.domain.R;
import com.laolang.zuke.framework.common.enums.CommonStatusCode;
import com.laolang.zuke.framework.common.exception.BusinessException;
import com.laolang.zuke.module.system.logic.SysDictLogic;
import com.laolang.zuke.module.system.req.SysDictTypeListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeListReq;
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
    public R<SysDictTypeListRsp> typeList(@RequestBody SysDictTypeListReq req) {
        throw new BusinessException(CommonStatusCode.ERROR);
    }

    @PostMapping("type/list1")
    public SysDictTypeListRsp typeList2(@RequestBody SysDictTypeListReq req) {
        SysDictTypeListRsp getrsp = getrsp();
        System.out.println(getrsp.getId());
        return sysDictLogic.typeList(req);
    }

    @PostMapping("type/list2")
    public String typeList3(@RequestBody SysDictTypeListReq req) {
        return "hello world";
    }

    @PostMapping("type/list3")
    public void typeList4(@RequestBody SysDictTypeListReq req) {
        log.info("list 3");
    }

    SysDictTypeListRsp getrsp(){
        return null;
    }
}
