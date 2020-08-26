package ru.bitmaster.paymentserver.critery;

import java.util.List;

public class AccountCritery extends ACritery {
    /**
     * Имя для поиска счета
     */
    private String name;

    /**
     * ID водителя
     */
    private Long clientId;

    /**
     * Имя водителя
     */
    private String clientName;

    public AccountCritery() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public AccountCritery(List<Long> ids) {
        super(ids);
    }

}
