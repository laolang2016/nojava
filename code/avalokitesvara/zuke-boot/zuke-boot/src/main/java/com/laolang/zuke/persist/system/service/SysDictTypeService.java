package com.laolang.zuke.persist.system.service;

import com.laolang.zuke.framework.mybatis.core.BaseService;
import com.laolang.zuke.persist.system.dto.SysDictTypeCountDto;
import com.laolang.zuke.persist.system.entity.SysDictType;
import java.util.List;

public interface SysDictTypeService extends BaseService<SysDictType> {
    List<SysDictTypeCountDto> countByIds(List<Long> ids);
}
