package ru.bitmaster.paymentserver.service;

import ru.bitmaster.paymentserver.critery.OperationCritery;
import ru.bitmaster.paymentserver.entity.Operation;

import java.util.List;

public interface OperationService extends ICrudService<Operation,Long> {
    List<Operation> findAll();
    List<Operation> findOperationsByIdsAndPageNum(Long[] ids, int pageNum);
    List<Operation> findByCritery(OperationCritery operationCritery);

}
