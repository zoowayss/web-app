package io.web.app.server.controller;

import io.web.app.common.domain.ApiResult;
import io.web.app.common.exception.CommonErrorEnum;
import io.web.app.common.utils.JsonUtils;
import io.web.app.server.controller.domain.CreateAccount;
import io.web.app.server.entity.Account;
import io.web.app.server.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 账户信息
 *
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@RequestMapping("/account")
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping
    public ApiResult<?> create(@RequestBody CreateAccount body) {
        if (body.getStatus() == null) {
            body.setStatus(Account.STATUS_NORMAL);
        }

        if (!CreateAccount.SELECTABLE_STATUS.contains(body.getStatus())) {
            return ApiResult.fail(CommonErrorEnum.PARAM_VALID.getCode(), String.format("status only support [%d,%d]", Account.STATUS_LOCKED, Account.STATUS_NORMAL));
        }
        if (!StringUtils.hasText(body.getAccount()) ||
                !StringUtils.hasText(body.getPassword()) ||
                !StringUtils.hasText(body.getName()) ||
                !StringUtils.hasText(body.getDescription())) {
            return ApiResult.fail(CommonErrorEnum.PARAM_VALID.getCode(), JsonUtils.toStr(body));
        }
        Account save = new Account();
        BeanUtils.copyProperties(body, save);
        return ApiResult.success(accountService.save(save));
    }


    @GetMapping
    public ApiResult<?> page(Pageable pageable,String name,Integer status,String account) {
        Page resp = accountService.selectPage(pageable, name, status, account);
        return ApiResult.success(resp);
    }

    @PutMapping("/{id}")
    public ApiResult<?> update(@PathVariable String id, String password, Integer status, String description) {
        Account account = accountService.getById(id);
        if (account == null) {
            return ApiResult.fail(CommonErrorEnum.PARAM_VALID.getCode(), String.format("account not exist, id=%s", id));
        }
        if (StringUtils.hasText(password)) {
            account.setPassword(password);
        }
        if (status != null && CreateAccount.SELECTABLE_STATUS.contains(status)) {
            account.setStatus(status);
        }
        if (StringUtils.hasText(description)) {
            account.setDescription(description);
        }
        return ApiResult.success(accountService.updateById(account));
    }

    @DeleteMapping("/{id}")
    public ApiResult<?> remove(@PathVariable String id) {
        return ApiResult.success(accountService.removeById(id));
    }

    @GetMapping("/like")
    public ApiResult<?> likeName(String name) {
        List<String> names = accountService.likeName(name);
        return ApiResult.success(names);
    }
}
