package ru.bitmaster.paymentserver.service;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.io.Serializable;

public interface ICrudService<T,PK extends Serializable> {
    T findById(PK id);
    T save(T o);
    void delete(T o);
    <L, M> BooleanExpression getBooleanExpression(
            L critery, M qEntity, Class<L> criteryClass, Class<M> qClass);

}
