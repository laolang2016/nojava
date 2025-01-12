package com.laolang.zuke.framework.mybatis.core;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * 数据库服务接口基类,所有的数据库接口都要继承此类.
 *
 * @author laolang
 * @version 0.1
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {

    /**
     * PageHelper 分页方式.
     *
     * @param page    第几页
     * @param size    页尺寸
     * @param wrapper mybatis-plus 分页条件
     * @return 分页结果
     */
    PageInfo<T> listPage(Integer page, Integer size, Wrapper<T> wrapper);
}
