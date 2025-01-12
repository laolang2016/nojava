package com.laolang.zuke.persist.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laolang.zuke.persist.system.entity.SysDictData;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDictDataMapper extends BaseMapper<SysDictData> {
    void deleteByTypeIds(@Param("typeIds") List<Long> typeIds);
}
