package com.laolang.zuke.persist.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laolang.zuke.framework.mybatis.core.BaseServiceImpl;
import com.laolang.zuke.persist.system.entity.SysDictData;
import com.laolang.zuke.persist.system.mapper.SysDictDataMapper;
import com.laolang.zuke.persist.system.service.SysDictDataService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {
    private final SysDictDataMapper sysDictDataMapper;

    @Override
    public void updateByGroupAndType(String groupCode, String type) {
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictData::getGroupCode, groupCode);
        wrapper.eq(SysDictData::getType, type);
        update(wrapper);
    }

    @Override
    public void deleteByTypeIds(List<Long> typeIds) {
        sysDictDataMapper.deleteByTypeIds(typeIds);
    }

    @Override
    public List<SysDictData> listByGroupCodeAndType(String groupCode, String type) {
        LambdaQueryWrapper<SysDictData> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictData::getGroupCode, groupCode);
        wrapper.eq(SysDictData::getType, type);
        return list(wrapper);
    }
}
