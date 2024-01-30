package io.web.app.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.web.app.file.entity.FileEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface FileMapper extends BaseMapper<FileEntity> {

    @Update("update s_file set ref_count = ref_count + #{delta} where id = #{id}")
    int incrementRefCount(@Param("id") String id, @Param("delta") int delta);

}