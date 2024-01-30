package io.web.app.file.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.web.app.file.entity.FileEntity;
import io.web.app.file.mapper.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService, FileLinker, CommandLineRunner {

    private static final int HOURS_24 = 60 * 60 * 24 * 1000;

    @Autowired
    private StorageService storageService;

    @Override
    public FileEntity findById(String id) {
        return getById(id);
    }

    @Transactional
    @Override
    public FileEntity saveFile(String userId, String filename, String contentType, InputStream inputStream) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(filename);
        fileEntity.setUserId(userId);
        fileEntity.setRefCount(0);
        fileEntity.setContentType(contentType);
        fileEntity.setCreateTime(new Date());
        save(fileEntity);
        storageService.saveFile(fileEntity.getId(),inputStream);
        return fileEntity;
    }

    @Override
    public void link(String key) throws FileNotFoundException {
        FileEntity fileEntity = getById(key);
        if (fileEntity == null) {
            throw new FileNotFoundException(key);
        }
        int ret = baseMapper.incrementRefCount(key, 1);
        if (ret == 0) {
            throw new FileNotFoundException();
        }
    }

    @Override
    public void unlink(String key) throws FileNotFoundException {
        int ret = baseMapper.incrementRefCount(key, -1);
        if (ret == 0) {
            throw new FileNotFoundException();
        }
    }

    private void cleanUnusedFiles() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ref_count", 0);
        queryWrapper.le("create_time", new Date(System.currentTimeMillis() - HOURS_24));
        List<FileEntity> list = baseMapper.selectList(queryWrapper);

        if (list.size() > 0) {
            for (FileEntity fileEntity : list) {
                try {
                    removeById(fileEntity.getId());
                    storageService.deleteFile(fileEntity.getId());
                } catch (FileNotFoundException e) {
                    log.warn("{} not exists, ignore it.", fileEntity.getId());
                }
            }
            log.info("Clean {} unused files", list.size());
        }
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            try {
                while (true) {
                    cleanUnusedFiles();
                    Thread.sleep(HOURS_24);
                }
            } catch (InterruptedException e) {

            }
        }, "File Clean Task").start();
    }
}