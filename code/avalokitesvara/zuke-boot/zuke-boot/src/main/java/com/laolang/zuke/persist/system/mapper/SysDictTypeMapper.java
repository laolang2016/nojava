package com.laolang.zuke.persist.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laolang.zuke.persist.system.dto.SysDictTypeCountDto;
import com.laolang.zuke.persist.system.entity.SysDictType;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    List<SysDictTypeCountDto> countByIds(@Param("ids") List<Long> ids);
}
