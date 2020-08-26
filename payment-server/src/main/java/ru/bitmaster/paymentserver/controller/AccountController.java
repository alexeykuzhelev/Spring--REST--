package ru.bitmaster.paymentserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bitmaster.paymentserver.controller.util.Accounts;
import ru.bitmaster.paymentserver.critery.AccountCritery;
import ru.bitmaster.paymentserver.entity.Account;
import ru.bitmaster.paymentserver.service.ACrudService;
import ru.bitmaster.paymentserver.service.AccountService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/account")
public class AccountController extends ASimpleRestController<Account,Long> {
    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Accounts> list(
            @RequestParam(value = "name_client", required = false) String nameClient,
            @RequestParam(value = "ids", required = false) List<Long> ids) {
//        logger.info("Param name_client:" + name_client + ";");
//        logger.info("Param ids:" + ids + ";");
        AccountCritery accountCritery = new AccountCritery();
        if (nameClient != null && !nameClient.isEmpty()) {
            accountCritery.setClientName(nameClient);
        }
        if (ids != null && ids.size() > 0) {
            accountCritery.setIds(ids);
        }
        List<Account> accounts = accountService.findByCritery(accountCritery);
        if (accounts.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Accounts>(new Accounts(accounts), HttpStatus.OK);
    }

    @Override
    public ACrudService<Account, Long> getService() {
        return (ACrudService<Account, Long>) accountService;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
