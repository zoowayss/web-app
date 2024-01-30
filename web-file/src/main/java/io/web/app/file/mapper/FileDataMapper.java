package io.web.app.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.web.app.file.entity.FileDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileDataMapper extends BaseMapper<FileDataEntity> {

    @Select("select size from s_file_data where id = #{id}")
    long getFileSize(@Param("id") String id);

}