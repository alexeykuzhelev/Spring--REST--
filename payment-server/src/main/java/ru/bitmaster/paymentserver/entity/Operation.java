package ru.bitmaster.paymentserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * Перевод со счета на счет
 */
@Entity
@Table(name = "operation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Operation extends AEntity {
    // Дата операции
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date ddate = new Date();
    // С какого счета снять
    private Account srcAccount;
    // На какой счет перевести
    private Account dstAccount;
    // Сумма перевода
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount = new BigDecimal("0.00");

    @Column
    @Temporal(TemporalType.DATE)
    public Date getDdate() {
        return ddate;
    }

    public void setDdate(Date ddate) {
        this.ddate = ddate;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "src_account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Account getSrcAccount() {
        return srcAccount;
    }

    public void setSrcAccount(Account srcAccount) {
        this.srcAccount = srcAccount;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "dst_account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Account getDstAccount() {
        return dstAccount;
    }

    public void setDstAccount(Account dstAccount) {
        this.dstAccount = dstAccount;
    }

    @Column
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        System.out.println("srcAccount: " + srcAccount);
        return "Operation{" +
                "id=" + id +
                ", srcAccount=" + srcAccount.getId() +
                ", dstAccount=" + dstAccount.getId() +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation that = (Operation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, srcAccount, dstAccount, amount);
    }
}
