package ru.bitmaster.paymentserver.service;

import ru.bitmaster.paymentserver.critery.ClientCritery;
import ru.bitmaster.paymentserver.entity.Client;

import java.util.List;

public interface ClientService extends ICrudService<Client,Long> {
    List<Client> findAll();
    Client findByName(String name);
    List<Client> findByIdIn(Long[] ids);
    List<Client> findByCritery(ClientCritery critery);

}
