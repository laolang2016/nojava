package com.laolang.zuke.framework.common.util;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

@UtilityClass
public class PageUtil {

    public <T, R> PageInfo<T> buildPage(PageInfo<R> page, Function<R, T> func) {
        PageInfo<T> p = new PageInfo<>();
        BeanUtils.copyProperties(page, p);
        if (CollUtil.isEmpty(page.getList())) {
            p.setList(Lists.newArrayList());
        } else {
            p.setList(page.getList().stream().map(func).collect(Collectors.toList()));
        }
        return p;
    }
}
