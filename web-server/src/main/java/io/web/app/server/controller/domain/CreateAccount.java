package io.web.app.server.controller.domain;

import io.web.app.server.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccount {
    public static final Set<Integer> SELECTABLE_STATUS = Set.of(Account.STATUS_LOCKED, Account.STATUS_NORMAL);
    private String name;

    private String description;

    private String account;
    private String password;
    private Integer status;
}
