package io.web.app.file.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.web.app.file.entity.FileDataEntity;
import io.web.app.file.mapper.FileDataMapper;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MysqlStorageService extends ServiceImpl<FileDataMapper, FileDataEntity> implements StorageService {

    @Override
    public long saveFile(String id, InputStream inputStream) throws IOException {
        FileDataEntity fileDataEntity = new FileDataEntity();
        fileDataEntity.setId(id);
        fileDataEntity.setData(StreamUtils.copyToByteArray(inputStream));
        fileDataEntity.setSize(fileDataEntity.getSize());
        save(fileDataEntity);
        return fileDataEntity.getData().length;
    }

    @Override
    public boolean deleteFile(String id) throws FileNotFoundException {
       return removeById(id);
    }

    @Override
    public InputStream getDataAsStream(String id) throws FileNotFoundException {
        FileDataEntity fileDataEntity = getById(id);
        if (fileDataEntity == null) {
            throw new FileNotFoundException(id);
        }
        return new ByteArrayInputStream(fileDataEntity.getData());
    }

    @Override
    public byte[] getDataAsBytes(String id) throws FileNotFoundException {
        FileDataEntity fileDataEntity = getById(id);
        if (fileDataEntity == null) {
            throw new FileNotFoundException(id);
        }
        return fileDataEntity.getData();
    }

    @Override
    public long getFileSize(String id) {
        return baseMapper.getFileSize(id);
    }
}