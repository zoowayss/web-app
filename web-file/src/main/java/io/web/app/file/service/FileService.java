package io.web.app.file.service;


import io.web.app.file.entity.FileEntity;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {


    FileEntity findById(String id);

    FileEntity saveFile(String userId, String filename, String contentType, InputStream inputStream) throws IOException;

}