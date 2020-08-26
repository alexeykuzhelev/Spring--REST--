package ru.bitmaster.paymentserver.controller.util;

import ru.bitmaster.paymentserver.entity.Operation;

import java.io.Serializable;
import java.util.List;

public class Operations implements Serializable {
    List<Operation> operations;

    public Operations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
