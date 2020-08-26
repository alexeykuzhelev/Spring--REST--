package ru.bitmaster.paymentserver.controller.util;

import ru.bitmaster.paymentserver.entity.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Accounts implements Serializable {
    private List<Account> accounts = new ArrayList<Account>();

    public Accounts() {

    }

    public Accounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
