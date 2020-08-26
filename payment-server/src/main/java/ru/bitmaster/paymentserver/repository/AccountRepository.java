package ru.bitmaster.paymentserver.repository;

import org.springframework.stereotype.Repository;
import ru.bitmaster.paymentserver.entity.Account;

@Repository
public interface AccountRepository extends BaseRespository<Account, Long> {
}
