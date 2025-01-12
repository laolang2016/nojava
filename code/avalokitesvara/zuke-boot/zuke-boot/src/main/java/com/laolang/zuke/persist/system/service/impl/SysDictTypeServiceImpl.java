package com.laolang.zuke.persist.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.laolang.zuke.framework.mybatis.core.BaseServiceImpl;
import com.laolang.zuke.persist.system.dto.SysDictTypeCountDto;
import com.laolang.zuke.persist.system.entity.SysDictType;
import com.laolang.zuke.persist.system.mapper.SysDictTypeMapper;
import com.laolang.zuke.persist.system.service.SysDictTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {
    private final SysDictTypeMapper sysDictTypeMapper;

    @Override
    public List<SysDictTypeCountDto> countByIds(List<Long> ids) {
        return sysDictTypeMapper.countByIds(ids);
    }

    @Override
    public List<SysDictType> listTypeByGroupCode(String groupCode) {
        LambdaQueryWrapper<SysDictType> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDictType::getGroupCode, groupCode);
        return list(wrapper);
    }
}
