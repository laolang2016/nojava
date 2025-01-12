package com.laolang.zuke.module.system.controller;

import com.github.pagehelper.PageInfo;
import com.laolang.zuke.module.system.logic.SysDictLogic;
import com.laolang.zuke.module.system.req.SysDictDataChangeStatusReq;
import com.laolang.zuke.module.system.req.SysDictDataDeleteReq;
import com.laolang.zuke.module.system.req.SysDictDataEditReq;
import com.laolang.zuke.module.system.req.SysDictDataListReq;
import com.laolang.zuke.module.system.req.SysDictTypeChangeStatusReq;
import com.laolang.zuke.module.system.req.SysDictTypeDeleteReq;
import com.laolang.zuke.module.system.req.SysDictTypeEditReq;
import com.laolang.zuke.module.system.req.SysDictTypeListReq;
import com.laolang.zuke.module.system.req.SysDictTypeTypeInfoListReq;
import com.laolang.zuke.module.system.rsp.SysDictDataListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeGroupInfoListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeListRsp;
import com.laolang.zuke.module.system.rsp.SysDictTypeTypeInfoListRsp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典管理
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("admin/system/dict")
@RestController
public class SysDictController {

    private final SysDictLogic sysDictLogic;

    /**
     * 字典类型分组信息
     */
    @PostMapping("type/groupInfo")
    public List<SysDictTypeGroupInfoListRsp> typeGroupInfo() {
        return sysDictLogic.typeGroupInfo();
    }

    /**
     * 字典类型列表
     */
    @PostMapping("type/list")
    public PageInfo<SysDictTypeListRsp> typeList(@RequestBody SysDictTypeListReq req) {
        return sysDictLogic.typeList(req);
    }

    /**
     * 编辑字典类型
     */
    @PostMapping("type/edit")
    public void typeEdit(@RequestBody SysDictTypeEditReq req) {
        sysDictLogic.edit(req);
    }

    /**
     * 删除字典类型
     */
    @PostMapping("type/delete")
    public void typeDelete(@Validated @RequestBody SysDictTypeDeleteReq req) {
        sysDictLogic.typeDelete(req);
    }

    /**
     * 更新字典类型状态
     */
    @PostMapping("type/changeStatus")
    public void typeChangeStatus(@Validated @RequestBody SysDictTypeChangeStatusReq req) {
        sysDictLogic.typeChangeStatus(req);
    }

    /**
     * 字典类型列表
     */
    @PostMapping("type/typeList")
    public List<SysDictTypeTypeInfoListRsp> typeTypeList(@Validated @RequestBody SysDictTypeTypeInfoListReq req) {
        return sysDictLogic.typeTypeList(req);
    }

    /**
     * 字典数据列表
     */
    @PostMapping("data/list")
    public PageInfo<SysDictDataListRsp> dataList(@RequestBody SysDictDataListReq req) {
        return sysDictLogic.dataList(req);
    }

    /**
     * 编辑字典数据
     */
    @PostMapping("data/edit")
    public void dataEdit(@RequestBody SysDictDataEditReq req) {
        sysDictLogic.dataEdit(req);
    }

    /**
     * 更新字典数据状态
     */
    @PostMapping("data/changeStatus")
    public void dataChangeStatus(@Validated @RequestBody SysDictDataChangeStatusReq req) {
        sysDictLogic.dataChangeStatus(req);
    }

    /**
     * 删除字典数据
     */
    @PostMapping("data/delete")
    public void dataDelete(@Validated @RequestBody SysDictDataDeleteReq req) {
        sysDictLogic.dataDelete(req);
    }
}
