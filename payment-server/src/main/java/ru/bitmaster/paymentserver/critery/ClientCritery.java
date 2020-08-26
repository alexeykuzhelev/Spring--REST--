package ru.bitmaster.paymentserver.critery;

import java.util.List;

public class ClientCritery extends  ACritery {
    /**
     * Имя для поиска
     */
    private String name;

    public ClientCritery() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientCritery(List<Long> ids, String name) {
        super(ids);
        this.name = name;
    }
}
