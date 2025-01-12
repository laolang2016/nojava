package com.laolang.zuke.framework.mybatis.core;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库服务实现基类,所有的数据库服务实现类都要继承此类.
 *
 * @author laolang
 * @version 0.1
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends
        ServiceImpl<M, T> implements BaseService<T> {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(T entity) {
        entity.setVersion(1);
        entity.setCreateTime(new Date());
        return super.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(T entity) {
        entity.setUpdateTime(new Date());
        return super.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchById(Collection<T> entityList) {
        if (CollUtil.isNotEmpty(entityList)) {
            for (T t : entityList) {
                t.setUpdateTime(new Date());
            }
        }
        return super.updateBatchById(entityList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList) {
        if (CollUtil.isNotEmpty(entityList)) {
            for (T t : entityList) {
                t.setVersion(1);
                t.setCreateTime(new Date());
            }
        }
        return super.saveBatch(entityList);
    }

    @Override
    public PageInfo<T> listPage(Integer page, Integer size, Wrapper<T> wrapper) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(list(wrapper));
    }
}
