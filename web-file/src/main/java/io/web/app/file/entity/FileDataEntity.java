package io.web.app.file.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("s_file_data")
public class FileDataEntity {
    private String id;

    private Long size;

    private byte[] data;
}
