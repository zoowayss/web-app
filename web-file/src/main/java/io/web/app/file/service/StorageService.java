package io.web.app.file.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface StorageService {

    long saveFile(String id, InputStream inputStream) throws IOException;


    boolean deleteFile(String id) throws FileNotFoundException;

    InputStream getDataAsStream(String id) throws  FileNotFoundException;

    byte[]  getDataAsBytes(String id) throws IOException;

    long getFileSize(String id) throws FileNotFoundException;
}
