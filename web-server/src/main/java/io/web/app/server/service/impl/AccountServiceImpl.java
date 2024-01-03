package io.web.app.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.web.app.common.utils.PageUtils;
import io.web.app.server.entity.Account;
import io.web.app.server.mapper.AccountMapper;
import io.web.app.server.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public Page selectPage(Pageable pageable, String name, Integer status, String account) {
        LambdaQueryWrapper<Account> query = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            query.eq(Account::getName, name);
        }
        if (status != null) {
            query.eq(Account::getStatus, status);
        }
        if (StringUtils.hasText(account)) {
            query.eq(Account::getAccount, account);
        }
        return PageUtils.request(page -> page(page, query), pageable);

    }

    @Override
    public List<String> likeName(String name) {
        LambdaQueryWrapper<Account> query = new LambdaQueryWrapper<Account>().select(Account::getName).likeRight(Account::getName, name);
        return listObjs(query, Object::toString);
    }
}
