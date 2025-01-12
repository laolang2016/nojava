package com.laolang.zuke.module.system.logic;

import com.laolang.zuke.module.system.req.SysDictTypeListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeListReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysDictLogic {
    public SysDictTypeListRsp typeList(SysDictTypeListReq req) {
        SysDictTypeListRsp rsp = new SysDictTypeListRsp();
        log.info("system dict list");
        return rsp;
    }
}
