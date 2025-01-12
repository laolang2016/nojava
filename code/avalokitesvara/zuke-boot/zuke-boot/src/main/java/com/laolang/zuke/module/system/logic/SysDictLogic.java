package com.laolang.zuke.module.system.logic;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.laolang.zuke.framework.common.enums.CommonEnums;
import com.laolang.zuke.framework.common.exception.BusinessException;
import com.laolang.zuke.framework.common.util.JsonUtil;
import com.laolang.zuke.framework.common.util.PageUtil;
import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
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
import com.laolang.zuke.persist.system.dto.SysDictTypeCountDto;
import com.laolang.zuke.persist.system.entity.SysDictData;
import com.laolang.zuke.persist.system.entity.SysDictType;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import com.laolang.zuke.persist.system.service.SysDictDataService;
import com.laolang.zuke.persist.system.service.SysDictTypeService;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SysDictLogic {

    private final SysDictTypeService sysDictTypeService;
    private final SysDictDataService sysDictDataService;

    /**
     * 字典分组列表
     */
    public List<SysDictTypeGroupInfoListRsp> typeGroupInfo() {
        List<SysDictTypeGroupInfoListRsp> list = Lists.newArrayList();
        SysDictEnums.GroupCode.value_map.forEach((s, groupCode) -> {
            SysDictTypeGroupInfoListRsp info = new SysDictTypeGroupInfoListRsp();
            info.setGroupCode(groupCode.getValue());
            info.setGroupName(groupCode.getDesc());
            list.add(info);
        });
        return list;
    }

    /**
     * 字典类型列表
     */
    public PageInfo<SysDictTypeListRsp> typeList(SysDictTypeListReq req) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(req.getName()), SysDictType::getName, req.getName());
        return PageUtil.buildPage(sysDictTypeService.listPage(req.getPage(), req.getSize(), wrapper), this::typeEntity2listRsp);
    }

    SysDictTypeListRsp typeEntity2listRsp(SysDictType entity) {
        SysDictTypeListRsp rsp = new SysDictTypeListRsp();
        BeanUtils.copyProperties(entity, rsp);
        SysDictEnums.GroupCode groupCode = SysDictEnums.GroupCode.value_map.get(entity.getGroupCode());
        if (Objects.isNull(groupCode)) {
            log.warn("字典分组不存在:{}", entity.getGroupCode());
        } else {
            rsp.setGroupName(groupCode.getDesc());
        }
        return rsp;
    }


    /**
     * 编辑字典类型
     */
    @Transactional(rollbackFor = Exception.class)
    public void edit(SysDictTypeEditReq req) {
        // 是否为编辑
        boolean isEdit = Objects.nonNull(req.getId());
        SysDictType entity = null;
        if (isEdit) {
            entity = assertDictTypeExistById(req.getId());
        }

        assertDityStatus(req.getStatus());
        assertDictGroupCode(req.getGroupCode());
        assertExistsByGroupCodeAndType(req.getId(), req.getGroupCode(), req.getType());

        if (isEdit) {
            // 编辑字典类型时,同步更新字典数据的字典分组和字典类型
            if (!StrUtil.equals(entity.getGroupCode(), req.getGroupCode()) || !StrUtil.equals(entity.getType(), req.getType())) {
                List<SysDictData> datas = sysDictDataService.listByGroupCodeAndType(entity.getGroupCode(), entity.getType());
                if (CollUtil.isNotEmpty(datas)) {
                    for (SysDictData data : datas) {
                        data.setGroupCode(req.getGroupCode());
                        data.setType(req.getType());
                    }
                    sysDictDataService.updateBatchById(datas);
                }
            }

            entity.setName(req.getName());
            entity.setType(req.getType());
            entity.setGroupCode(req.getGroupCode());
            entity.setStatus(req.getStatus());
            sysDictTypeService.updateById(entity);
        } else {
            SysDictType type = new SysDictType();
            type.setName(req.getName());
            type.setType(req.getType());
            type.setGroupCode(req.getGroupCode());
            type.setStatus(req.getStatus());
            sysDictTypeService.save(type);
        }
    }

    /**
     * 删除字典类型
     */
    @Transactional(rollbackFor = Exception.class)
    public void typeDelete(SysDictTypeDeleteReq req) {
        assertDictTypeExistByIds(req.getIds());

        List<SysDictTypeCountDto> typeCountDtos = sysDictTypeService.countByIds(req.getIds());
        log.info("typeCountDtos:{}", JsonUtil.toJsonString(typeCountDtos));

        if (typeCountDtos.stream().anyMatch(SysDictTypeCountDto::hasEnabled)) {
            log.warn("不允许删除还有启用中的值的字典类型. req:{}", JsonUtil.toJsonString(req));
            throw new BusinessException("字典类型下还有启用中的字典值, 不允许删除");
        }

        sysDictDataService.deleteByTypeIds(req.getIds());
        sysDictTypeService.removeBatchByIds(req.getIds());

    }

    /**
     * 更细字典类型状态
     */
    @Transactional(rollbackFor = Exception.class)
    public void typeChangeStatus(SysDictTypeChangeStatusReq req) {
        SysDictType type = assertDictTypeExistById(req.getId());

        assertDityStatus(req.getStatus());

        // 更新字典数据状态
        if (CommonEnums.YesNoChar.yes(req.getUpdateData())) {
            sysDictDataService.updateByGroupAndType(type.getGroupCode(), type.getType(), req.getStatus());
        }
        type.setStatus(req.getStatus());
        sysDictTypeService.updateById(type);
    }

    /**
     * 字典类型列表
     */
    public List<SysDictTypeTypeInfoListRsp> typeTypeList(SysDictTypeTypeInfoListReq req) {
        assertDictGroupCode(req.getGroupCode());
        List<SysDictType> types = sysDictTypeService.listTypeByGroupCode(req.getGroupCode());
        return types.stream().map(sysDictType -> {
            SysDictTypeTypeInfoListRsp rsp = new SysDictTypeTypeInfoListRsp();
            rsp.setType(sysDictType.getType());
            rsp.setName(sysDictType.getName());
            return rsp;
        }).collect(Collectors.toList());
    }

    /**
     * 字典数据列表
     */
    public PageInfo<SysDictDataListRsp> dataList(SysDictDataListReq req) {
        assertDictGroupCode(req.getGroupCode());
        if (StrUtil.isNotBlank(req.getStatus())) {
            assertDityStatus(req.getStatus());
        }

        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StrUtil.isNotBlank(req.getType()), SysDictData::getType, req.getType());
        wrapper.eq(StrUtil.isNotBlank(req.getGroupCode()), SysDictData::getGroupCode, req.getGroupCode());
        wrapper.eq(StrUtil.isNotBlank(req.getStatus()), SysDictData::getStatus, req.getStatus());
        wrapper.like(StrUtil.isNotBlank(req.getLabel()), SysDictData::getLabel, req.getLabel());
        return PageUtil.buildPage(sysDictDataService.listPage(req.getPage(), req.getSize(), wrapper), new Function<SysDictData, SysDictDataListRsp>() {
            @Override
            public SysDictDataListRsp apply(SysDictData sysDictData) {
                SysDictDataListRsp rsp = new SysDictDataListRsp();
                BeanUtils.copyProperties(sysDictData, rsp);
                return rsp;
            }
        });
    }

    /**
     * 编辑字典数据
     */
    public void dataEdit(SysDictDataEditReq req) {
        boolean isEdit = Objects.nonNull(req.getId());
        SysDictData entity = null;
        if (isEdit) {
            entity = assertDictDataExistById(req.getId());
        }

        assertDityStatus(req.getStatus());
        assertDictDefaultValue(req.getDefaultValue());
        assertDictGroupCode(req.getGroupCode());
        assertDictGroupCode(req.getGroupCode(), req.getType());

        assertTypeDataExistForEdit(req);

        if (isEdit) {
            if (StrUtil.equals(entity.getGroupCode(), req.getGroupCode()) || StrUtil.equals(entity.getType(), req.getType())) {
                log.error("字典数据分组和类型不允许修改. req:{}", JsonUtil.toJsonString(req));
            }
            entity.setLabel(req.getLabel());
            entity.setValue(req.getValue());
            entity.setDefaultValue(req.getDefaultValue());
            entity.setStatus(req.getStatus());
            sysDictDataService.updateById(entity);

            // 同一分组统一类型下,只允许有一个默认值
            if (CommonEntityEnums.DefaultValue.yes(req.getDefaultValue())) {
                UpdateWrapper<SysDictData> update = Wrappers.update();
                update.lambda().eq(SysDictData::getGroupCode, req.getGroupCode())
                        .eq(SysDictData::getType, req.getType())
                        .ne(SysDictData::getId, req.getId())
                        .set(SysDictData::getDefaultValue, CommonEntityEnums.DefaultValue.NO.getValue());
                sysDictDataService.update(update);
            }
        } else {
            SysDictData insert = new SysDictData();
            insert.setType(req.getType());
            insert.setGroupCode(req.getGroupCode());
            insert.setLabel(req.getLabel());
            insert.setValue(req.getValue());
            insert.setDefaultValue(req.getDefaultValue());
            insert.setStatus(req.getStatus());

            sysDictDataService.save(insert);
        }
    }


    /**
     * 更新字典数据状态
     */
    public void dataChangeStatus(SysDictDataChangeStatusReq req) {
        assertDityStatus(req.getStatus());
        SysDictData data = assertDictDataExistById(req.getId());
        data.setStatus(req.getStatus());
        sysDictDataService.updateById(data);
    }

    /**
     * 删除字典数据
     */
    public void dataDelete(SysDictDataDeleteReq req) {
        assertDictDataExistByIds(req.getIds());
        sysDictDataService.removeBatchByIds(req.getIds());
    }

    /**
     * 断言字典数据是否存在
     */
    void assertTypeDataExistForEdit(SysDictDataEditReq req) {
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(Objects.nonNull(req.getId()), SysDictData::getId, req.getId());
        wrapper.eq(SysDictData::getGroupCode, req.getGroupCode());
        wrapper.eq(SysDictData::getType, req.getType());
        wrapper.eq(SysDictData::getValue, req.getValue());
        long count = sysDictDataService.count(wrapper);
        if (count > 0) {
            log.error("字典数据已存在. req:{}", JsonUtil.toJsonString(req));
            throw new BusinessException("字典数据已存在");
        }
    }

    /**
     * 根据 id 断言字典类型是否存在
     *
     * @param id 字典类型 id
     */
    SysDictData assertDictDataExistById(Long id) {
        SysDictData type = sysDictDataService.getById(id);
        if (Objects.isNull(type)) {
            throw new BusinessException("字典数据信息不存在");
        }
        return type;
    }

    /**
     * 根据 id 列表断言字典类型是否存在
     *
     * @param ids 字典类型 id 列表
     */
    void assertDictDataExistByIds(List<Long> ids) {
        List<SysDictData> datas = sysDictDataService.listByIds(ids);
        if (datas.size() != ids.size()) {
            throw new BusinessException("字典类型信息不存在");
        }
    }

    /**
     * 断言字典是否默认值是否正确
     *
     * @param defaultValue 字典是否默认值
     */
    void assertDictDefaultValue(String defaultValue) {
        if (!CommonEntityEnums.DefaultValue.hasValue(defaultValue)) {
            throw new BusinessException("字典是否默认值不正确");
        }
    }

    /**
     * 根据字典分组和字典类型断言字典类型是否已经存在
     *
     * @param id        字典类型 id
     * @param groupCode 字典分组
     * @param type      字典类型
     */
    void assertExistsByGroupCodeAndType(Long id, String groupCode, String type) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(Objects.nonNull(id), SysDictType::getId, id);
        wrapper.eq(SysDictType::getGroupCode, groupCode);
        wrapper.eq(SysDictType::getType, type);
        long count = sysDictTypeService.count(wrapper);
        if (0 != count) {
            throw new BusinessException("字典类型已存在");
        }
    }

    /**
     * 断言字典分组是否存在
     *
     * @param groupCode 字典分组编码
     */
    void assertDictGroupCode(String groupCode) {
        if (!SysDictEnums.GroupCode.value_map.containsKey(groupCode)) {
            throw new BusinessException("字典分组不存在");
        }
    }

    /**
     * 断言字典类型是否存在
     *
     * @param groupCode 字典分组编码
     * @param type      字典类型
     */
    void assertDictGroupCode(String groupCode, String type) {
        List<SysDictType> types = sysDictTypeService.listTypeByGroupCode(groupCode);
        if (CollUtil.isEmpty(types) || !types.stream().map(SysDictType::getType).collect(Collectors.toList()).contains(type)) {
            throw new BusinessException("字典类型不存在");
        }
    }

    /**
     * 断言字典状态是否正确
     *
     * @param status 字典类型状态
     */
    void assertDityStatus(String status) {
        if (!CommonEntityEnums.Status.hasValue(status)) {
            throw new BusinessException("状态值非法");
        }
    }

    /**
     * 根据 id 列表断言字典类型是否存在
     *
     * @param ids 字典类型 id 列表
     */
    void assertDictTypeExistByIds(List<Long> ids) {
        List<SysDictType> types = sysDictTypeService.listByIds(ids);
        if (types.size() != ids.size()) {
            throw new BusinessException("字典类型信息不存在");
        }
    }


    /**
     * 根据 id 断言字典类型是否存在
     *
     * @param id 字典类型 id
     */
    SysDictType assertDictTypeExistById(Long id) {
        SysDictType type = sysDictTypeService.getById(id);
        if (Objects.isNull(type)) {
            throw new BusinessException("字典类型信息不存在");
        }
        return type;
    }


}
