package io.web.app.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_account", autoResultMap = true)
public class Account {

    public static final int STATUS_LOCKED = 0;
    public static final int STATUS_NORMAL = 1;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "`name`")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    @TableField(value = "`status`")
    private Integer status;

    @TableField(value = "account")
    private String account;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}