package com.laolang.zuke.persist.system.entity;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.laolang.zuke.framework.mybatis.core.BaseEntity;
import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

@KeySequence(value = "seq_sys_dict_data", dbType = DbType.ORACLE)
@TableName("sys_dict_data")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDictData extends BaseEntity {

    /**
     * 字典类型
     *
     * @see SysDictEnums.Type
     */
    private String type;

    /**
     * 字典所在分组
     *
     * @see SysDictEnums.GroupCode
     */
    private String groupCode;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 是否默认值
     *
     * @see CommonEntityEnums.DefaultValue
     */
    private String defaultValue;

    /**
     * 字典状态
     *
     * @see CommonEntityEnums.Status
     */
    private String status;
}
