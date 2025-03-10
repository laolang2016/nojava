package com.laolang.zuke.persist.system.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.laolang.zuke.framework.mybatis.core.BaseEntity;
import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

@KeySequence(value = "seq_sys_dict_type", dbType = DbType.ORACLE)
@TableName("sys_dict_type")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictType extends BaseEntity {
    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     *
     * @see SysDictEnums.Type
     */
    private String type;

    /**
     * 字典分组
     *
     * @see SysDictEnums.GroupCode
     */
    private String groupCode;

    /**
     * 字典分类状态
     *
     * @see CommonEntityEnums.Status
     */
    private String status;

}
