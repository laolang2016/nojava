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
    private String name;
    /**
     * @see SysDictEnums.Type
     */
    private String type;
    /**
     * @see SysDictEnums.GroupCode
     */
    private String groupCode;
    /**
     * @see CommonEntityEnums.Status
     */
    private String status;

}
