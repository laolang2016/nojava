package com.laolang.zuke.persist.system.service;

import com.laolang.zuke.framework.mybatis.core.BaseService;
import com.laolang.zuke.persist.system.entity.SysDictData;
import java.util.List;

public interface SysDictDataService extends BaseService<SysDictData> {
    void updateByGroupAndType(String groupCode, String type, String status);

    void deleteByTypeIds(List<Long> typeIds);

    List<SysDictData> listByGroupCodeAndType(String groupCode, String type);
}
