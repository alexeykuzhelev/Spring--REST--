package ru.bitmaster.paymentserver.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@NoRepositoryBean
public interface BaseRespository<T, ID extends Serializable> extends
        PagingAndSortingRepository<T, ID>, QuerydslPredicateExecutor<T> {
}
