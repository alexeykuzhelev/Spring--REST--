package ru.bitmaster.paymentserver.critery;

import java.util.Date;
import java.util.List;

public class OperationCritery extends ACritery {
    /**
     * Период с
     */
    private Date toDate;

    /**
     * Период по
     */
    private Date fromDate;

    /**
     * Номер счета, с которого было перечисление
     */
    private Long srcAccountId;

    /**
     * Номер счета, на который было перечисление
     */
    private Long dstAccountId;

    public OperationCritery() {

    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Long getSrcAccountId() {
        return srcAccountId;
    }

    public void setSrcAccountId(Long srcAccountId) {
        this.srcAccountId = srcAccountId;
    }

    public Long getDstAccountId() {
        return dstAccountId;
    }

    public void setDstAccountId(Long dstAccountId) {
        this.dstAccountId = dstAccountId;
    }

    public OperationCritery(List<Long> ids) {
        super(ids);
    }

}
