package ru.bitmaster.paymentserver.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AEntity implements Serializable {

    protected Long id;

    @Id
    @SequenceGenerator(name = "entityIdSeq", sequenceName = "entity_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entityIdSeq")
    @Column
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
