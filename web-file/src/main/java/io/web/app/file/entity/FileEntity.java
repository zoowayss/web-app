package io.web.app.file.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("s_file")
public class FileEntity {

    @TableId(type = IdType.AUTO)

    private String id;

    private String userId;

    private String encode;

    private Integer refCount;

    private String contentType;

    private String filename;

    private Date createTime;
}
