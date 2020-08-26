package ru.bitmaster.paymentserver.service;

import ru.bitmaster.paymentserver.critery.AccountCritery;
import ru.bitmaster.paymentserver.entity.Account;

import java.util.List;

public interface AccountService extends ICrudService<Account,Long> {
    List<Account> findAll();
    Account findByName(String name);
    List<Account> findByIdIn(Long[] ids);
    List<Account> findByCritery(AccountCritery critery);

}
