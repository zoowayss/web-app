package io.web.app.file.config;

import io.web.app.file.service.MysqlStorageService;
import io.web.app.file.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {


    @Bean
    public StorageService defaultStorageService(){
        return new MysqlStorageService();
    }
}
