package com.laolang.zuke.persist.system.service.impl;

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
}
