package com.laolang.zuke.module.system.req;

import com.laolang.zuke.framework.mybatis.enums.CommonEntityEnums;
import com.laolang.zuke.persist.system.enums.SysDictEnums;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SysDictDataEditReq {
    private Long id;
    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String label;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String value;

    /**
     * 是否默认值
     *
     * @see CommonEntityEnums.DefaultValue
     */
    @NotBlank(message = "字典是否默认值不能为空")
    private String defaultValue;

    /**
     * 字典状态
     *
     * @see CommonEntityEnums.Status
     */
    @NotBlank(message = "字典状态不能为空")
    private String status;

    /**
     * 字典类型
     *
     * @see SysDictEnums.Type
     */
    @NotBlank(message = "字典类型不能为空")
    private String type;

    /**
     * 字典所在分组
     *
     * @see SysDictEnums.GroupCode
     */
    @NotBlank(message = "字典分组不能为空")
    private String groupCode;
}
