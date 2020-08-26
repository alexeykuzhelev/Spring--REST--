package ru.bitmaster.paymentserver.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bitmaster.paymentserver.critery.AccountCritery;
import ru.bitmaster.paymentserver.entity.Account;
import ru.bitmaster.paymentserver.entity.QAccount;
import ru.bitmaster.paymentserver.repository.AccountRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service("accountService")
@Transactional
public class AccountServiceImpl extends ACrudService<Account, Long>
        implements AccountService {

    @Autowired(required=false)
    AccountRepository accountRepository;

    @Override
    public AccountRepository getRepository() {
        return accountRepository;
    }

    @Override
    public Account findByName(String name) {
        AccountCritery critery = new AccountCritery();
        critery.setName(name);
        List<Account> accounts = findByCritery(critery);
        return accounts.size()>0? accounts.get(0):null;
    }

    @Override
    public List<Account> findByIdIn(Long[] ids) {
        AccountCritery critery = new AccountCritery();
        critery.setIds(Arrays.asList(ids));
        return findByCritery(critery);
    }

    @Override
    public List<Account> findByCritery(AccountCritery critery) {
        QAccount qAccount = QAccount.account;
        int pageSize = 5;
        int pageNum = critery.getPageNum();
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        BooleanExpression expression = getBooleanExpression(
                critery, qAccount, AccountCritery.class, QAccount.class);
        Page<Account> page = accountRepository.findAll(expression, pageable);
        List<Account> listAccounts = page.getContent();
        return listAccounts;
    }

}
