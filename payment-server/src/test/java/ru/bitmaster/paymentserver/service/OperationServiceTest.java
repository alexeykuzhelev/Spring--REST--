package ru.bitmaster.paymentserver.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bitmaster.paymentserver.critery.OperationCritery;
import ru.bitmaster.paymentserver.entity.Account;
import ru.bitmaster.paymentserver.entity.Operation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OperationServiceTest {
    @Autowired
    OperationService operationService;
    @Autowired
    AccountService accountService;

    @Test
    public void contextLoads() {
        List<Operation> operations = operationService.findAll();
        assert operations.size() == 2;
    }

    @Test
    public void findByIdTest() {
        Long id = new Long(0);
        Operation operation = operationService.findById(id);
        assert operation.getId().equals(id);
    }

    @Test
    public void saveTest() {
        Long id = new Long(0);
        Operation operation = operationService.findById(id);
        BigDecimal amount = new BigDecimal("20.00");
        BigDecimal srcBalance = accountService.findById(operation.getSrcAccount().getId()).getBalance();
        BigDecimal dstBalance = accountService.findById(operation.getDstAccount().getId()).getBalance();
        operation.setAmount(amount);
        Operation c = operationService.save(operation);
        assert c.getAmount().compareTo(amount) == 0;
        assert accountService.findById(operation.getSrcAccount().getId()).getBalance().compareTo(srcBalance) == 0;
        assert accountService.findById(operation.getDstAccount().getId()).getBalance().compareTo(dstBalance) == 0;
    }

    @Test
    public void createTest() {
        Long srcAccountId = new Long(1);
        Long dstAccountId = new Long(2);
        Account srcAccount = accountService.findById(srcAccountId);
        Account dstAccount = accountService.findById(dstAccountId);
        BigDecimal srcBalance = srcAccount.getBalance();
        BigDecimal dstBalance = dstAccount.getBalance();
        BigDecimal amount = new BigDecimal("30.00");
        Operation operation = new Operation();
        operation.setSrcAccount(srcAccount);
        operation.setDstAccount(dstAccount);
        operation.setAmount(amount);
        Operation newOperation = operationService.save(operation);
        System.out.println("newOperation : " + newOperation);
        // Проверка полей
        assert newOperation.getAmount().compareTo(amount) == 0;
//        assert newOperation.getId().equals(new Long(2));
        assert newOperation.getSrcAccount().getId().equals(srcAccount.getId());
        assert newOperation.getDstAccount().getId().equals(dstAccount.getId());
        // Проверка изменения балансов
        assert newOperation.getSrcAccount().getBalance().add(amount).compareTo(srcBalance) == 0;
        assert newOperation.getDstAccount().getBalance().subtract(amount).compareTo(srcBalance) == 0;
    }

    @Test
    public void deleteTest() {
        Long id = new Long(1);
        Operation operation = operationService.findById(id);
        BigDecimal amount = operation.getAmount();
        BigDecimal srcBalance = accountService.findById(operation.getSrcAccount().getId()).getBalance();
        BigDecimal dstBalance = accountService.findById(operation.getDstAccount().getId()).getBalance();
        operationService.delete(operation);
        List<Operation> operations = operationService.findAll();
        assert operations.size() == 1;
        // Проверка изменения балансов
        Account srcAccount = accountService.findById(operation.getSrcAccount().getId());
        Account dstAccount = accountService.findById(operation.getDstAccount().getId());
        assert srcBalance.add(amount).compareTo(srcAccount.getBalance()) == 0;
        assert dstBalance.subtract(amount).compareTo(dstAccount.getBalance()) == 0;
    }

    @Test
    public void findOperationsByIdsAndPageNumTest() {
        int pageNum = 1;
        Long[] ids = new Long[]{new Long(0), new Long(1)};
        List<Operation> operations = operationService.findOperationsByIdsAndPageNum(ids, pageNum);
        assert operations.size() == 2;
        ids = new Long[]{new Long(1)};
        operations = operationService.findOperationsByIdsAndPageNum(ids, pageNum);
        assert operations.size() == 1;
    }

    @Test
    public void testGetByCriteryToDate() {
        OperationCritery operationCritery = new OperationCritery();
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 8, 12);
        operationCritery.setToDate(cal.getTime());
        operationCritery.setPageNum(1);
        List<Operation> operations = operationService.findByCritery(operationCritery);
        assert operations.size() == 2;
        assert operations.get(0).getId().compareTo(new Long(0)) == 0;
    }

    @Test
    public void testGetByCriterySrcAccount() {
        Long accountId = new Long(1);
        OperationCritery operationCritery = new OperationCritery();
        operationCritery.setPageNum(1);
        operationCritery.setSrcAccountId(accountId);
        List<Operation> operations = operationService.findByCritery(operationCritery);
        assert operations.size() == 1;
        assert operations.get(0).getSrcAccount().getId().compareTo(accountId) == 0;
    }

    @Test
    public void testGetByCriteryDstAccount() {
        Long accountId = new Long(2);
        OperationCritery operationCritery = new OperationCritery();
        operationCritery.setPageNum(1);
        operationCritery.setDstAccountId(accountId);
        List<Operation> operations = operationService.findByCritery(operationCritery);
        assert operations.size() == 1;
        assert operations.get(0).getDstAccount().getId().compareTo(accountId) == 0;
    }

}
