package ru.bitmaster.paymentserver.repository;

import org.springframework.stereotype.Repository;
import ru.bitmaster.paymentserver.entity.Client;

@Repository
public interface ClientRepository extends BaseRespository<Client, Long> {
}
