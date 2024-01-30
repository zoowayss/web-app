package io.web.app.file.service;

import java.io.FileNotFoundException;

public interface FileLinker {

    void link(String key) throws FileNotFoundException;

    void unlink(String key) throws FileNotFoundException;
}