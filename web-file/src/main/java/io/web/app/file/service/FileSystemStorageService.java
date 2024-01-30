package io.web.app.file.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;

import java.io.*;

public class FileSystemStorageService implements StorageService, InitializingBean {

    @Value("${app.file.basePath}")
    private String basePath;

    @Override
    public long saveFile(String id, InputStream inputStream) throws IOException {
        File file = new File(basePath + "/" + id);

        FileOutputStream outputStream = null;
        try {

            outputStream = new FileOutputStream(file);
            StreamUtils.copy(inputStream, outputStream);

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }

        return file.length();
    }

    @Override
    public boolean deleteFile(String id) throws FileNotFoundException {
        File file = new File(basePath + "/" + id);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            throw new FileNotFoundException(id);
        }
    }

    @Override
    public InputStream getDataAsStream(String id) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(basePath + "/" + id));
    }

    @Override
    public byte[] getDataAsBytes(String id) throws IOException {
        return StreamUtils.copyToByteArray(getDataAsStream(id));
    }

    @Override
    public long getFileSize(String id) {
        return new File(basePath + "/" + id).length();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        File file = new File(basePath);

        if (file.exists()) {
            if (file.isFile()) {
                throw new IllegalArgumentException("basePath must be a directory");
            }
        } else {
            file.mkdirs();
        }

    }
}