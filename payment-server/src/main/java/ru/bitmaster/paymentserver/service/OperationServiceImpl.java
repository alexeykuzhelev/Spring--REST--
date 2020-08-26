package ru.bitmaster.paymentserver.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bitmaster.paymentserver.critery.OperationCritery;
import ru.bitmaster.paymentserver.entity.*;
import ru.bitmaster.paymentserver.repository.AccountRepository;
import ru.bitmaster.paymentserver.repository.OperationRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service("operationService")
@Transactional
public class OperationServiceImpl extends ACrudService<Operation, Long>
        implements OperationService {

    @Autowired(required=false)
    OperationRepository operationRepository;

    @Autowired(required=false)
    AccountRepository accountRepository;

    @Override
    OperationRepository getRepository() {
        return operationRepository;
    }

    @Override
    public List<Operation> findOperationsByIdsAndPageNum(Long[] ids, int pageNum) {
        OperationCritery operationCritery = new OperationCritery();
        operationCritery.setPageNum(pageNum);
        operationCritery.setIds(Arrays.asList(ids));
        return findByCritery(operationCritery);
    }

    @Override
    public List<Operation> findByCritery(OperationCritery critery) {
        QOperation qOperation = QOperation.operation;
        int pageSize = 5;
        int pageNum = critery.getPageNum();
        Sort sort = Sort.by("ddate");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        BooleanExpression expression = getBooleanExpression(
                critery, qOperation, OperationCritery.class, QOperation.class);
        Page<Operation> page = operationRepository.findAll(expression, pageable);
        List<Operation> listOperations = page.getContent();
        return listOperations;
    }

    /**
     * Корректировка балансов
     *
     * @param srcAccount -
     * @param dstAccount
     * @param amount
     */
    public void correctBalance(Account srcAccount, Account dstAccount, BigDecimal amount) {
        srcAccount = accountRepository.findById(srcAccount.getId()).orElse(null);
        dstAccount = accountRepository.findById(dstAccount.getId()).orElse(null);
        srcAccount.setBalance(srcAccount.getBalance().subtract(amount));
        dstAccount.setBalance(dstAccount.getBalance().add(amount));
    }

    @Override
    public Operation save(Operation operation) {
        if(operation.getId() != null && findById(operation.getId()) != null) {
            Operation oldOperation = findById(operation.getId());
            correctBalance(oldOperation.getDstAccount(), oldOperation.getSrcAccount()
                    , oldOperation.getAmount());
        }
        correctBalance(operation.getSrcAccount(), operation.getDstAccount()
                , operation.getAmount());
        return super.save(operation);
    }

    @Override
    public void delete(Operation operation) {
        correctBalance(operation.getDstAccount(), operation.getSrcAccount()
                , operation.getAmount());
        super.delete(operation);
    }

    @Override
    public void delete(Long id) {
        Operation operation = findById(id);
        this.delete(operation);
    }

}
