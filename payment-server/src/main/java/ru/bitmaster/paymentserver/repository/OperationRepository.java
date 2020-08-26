package ru.bitmaster.paymentserver.repository;

import org.springframework.stereotype.Repository;
import ru.bitmaster.paymentserver.entity.Operation;

@Repository
public interface OperationRepository extends BaseRespository<Operation, Long> {
}
