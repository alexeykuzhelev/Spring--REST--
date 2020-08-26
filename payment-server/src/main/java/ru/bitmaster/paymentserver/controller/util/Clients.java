package ru.bitmaster.paymentserver.controller.util;

import java.io.Serializable;
import java.util.List;

import ru.bitmaster.paymentserver.entity.Client;

public class Clients implements Serializable {
    private List<Client> clients;

    public Clients() {
    }

    public Clients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
