package io.web.app.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.web.app.server.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService extends IService<AccountEntity>{


    Page selectPage(Pageable pageable, String name, Integer status, String account);

    List<String> likeName(String name);
}
