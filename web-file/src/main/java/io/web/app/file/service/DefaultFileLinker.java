package io.web.app.file.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(value = FileLinker.class)
public class DefaultFileLinker implements FileLinker {

    @Override
    public void link(String key) {

    }

    @Override
    public void unlink(String key) {

    }
}