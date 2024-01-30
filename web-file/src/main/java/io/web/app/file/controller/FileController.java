package io.web.app.file.controller;


import io.web.app.common.domain.ApiResult;
import io.web.app.common.domain.RequestHolder;
import io.web.app.file.entity.FileEntity;
import io.web.app.file.service.FileService;
import io.web.app.file.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("/file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private StorageService storageService;

    @PostMapping
    public ApiResult uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        FileEntity fileEntity = fileService.saveFile(RequestHolder.get().getUid(), file.getOriginalFilename(), file.getContentType(), file.getInputStream());
        return ApiResult.success(fileEntity.getId());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        FileEntity fileEntity = fileService.findById(id);
        if (fileEntity == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setContentLengthLong(storageService.getFileSize(fileEntity.getId()));
            response.setContentType(fileEntity.getContentType());
            InputStream dataAsStream = storageService.getDataAsStream(id);
            StreamUtils.copy(dataAsStream, response.getOutputStream());
        }
    }


}