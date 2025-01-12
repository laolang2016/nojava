package com.laolang.zuke.module.system.logic;

import com.laolang.zuke.module.system.req.SysDictTypeListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeListReq;
import com.laolang.zuke.persist.system.entity.SysDictType;
import com.laolang.zuke.persist.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SysDictLogic {

    private final SysDictTypeService sysDictTypeService;

    public SysDictTypeListRsp typeList(SysDictTypeListReq req) {
        SysDictTypeListRsp rsp = new SysDictTypeListRsp();
        log.info("system dict list");
        sysDictTypeService.getById(2L);

        SysDictType insert = new SysDictType();
        insert.setType("1");
        sysDictTypeService.save(insert);
        return rsp;
    }
}
