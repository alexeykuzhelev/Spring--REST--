package ru.bitmaster.paymentserver.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.bitmaster.paymentserver.critery.ACritery;
import ru.bitmaster.paymentserver.critery.AccountCritery;
import ru.bitmaster.paymentserver.critery.ClientCritery;
import ru.bitmaster.paymentserver.critery.OperationCritery;
import ru.bitmaster.paymentserver.entity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class ACrudService<T, PK extends Serializable> {

    abstract PagingAndSortingRepository<T,PK> getRepository();

    public T findById(PK id) {
        T entity = getRepository().findById(id).orElse(null);
        return entity;
    }

    public T save(T o) {
        T ret = getRepository().save(o);
        return ret;
    }

    public void delete(T o) {
        getRepository().delete(o);
    }

    public List<T> findAll() {
        Iterable<T> iterEntities = getRepository().findAll();
        return iterableToList(iterEntities);
    }

    public <L, M> BooleanExpression getBooleanExpression(
            L critery, M qEntity, Class<L> criteryClass, Class<M> qClass) {
        String name = null;
        Long clientId = null;
        String clientName = null;
        Date fromDate = null;
        Date toDate = null;
        Long srcAccountId = null;
        Long dstAccountId = null;
        NumberPath<Long> qEntityId;
        qEntityId = criteryClass.getTypeName().contains("ru.bitmaster.paymentserver.critery.AccountCritery")
                ? criteryClass.getTypeName().contains("ru.bitmaster.paymentserver.critery.ClientCritery")
                ? criteryClass.getTypeName().contains("ru.bitmaster.paymentserver.critery.OperationCritery") ? null
                : ((QOperation) qEntity).id : ((QClient) qEntity).id : ((QOperation) qEntity).id;
        switch(criteryClass.getTypeName()){
            case "ru.bitmaster.paymentserver.critery.AccountCritery":
                name = ((AccountCritery) critery).getName();
                clientId = ((AccountCritery) critery).getClientId();
                clientName = ((AccountCritery) critery).getClientName();
                break;
            case "ru.bitmaster.paymentserver.critery.ClientCritery":
                name = ((ClientCritery) critery).getName();
                break;
            case "ru.bitmaster.paymentserver.critery.OperationCritery":
                fromDate = ((OperationCritery) critery).getFromDate();
                toDate = ((OperationCritery) critery).getToDate();
                srcAccountId = ((OperationCritery) critery).getSrcAccountId();
                dstAccountId = ((OperationCritery) critery).getDstAccountId();
                break;
            default:
                throw new IllegalStateException("Unexpected value: "
                        + criteryClass.getTypeName());
        }
        StringPath nameQEntity = null;
        QClient clientQEntity = null;
        QOperation qEntityOperation = null;
        switch(qClass.getTypeName()){
            case "ru.bitmaster.paymentserver.entity.QAccount":
                nameQEntity = ((QAccount) qEntity).name;
                clientQEntity = ((QAccount) qEntity).client;
                break;
            case "ru.bitmaster.paymentserver.entity.QClient":
                nameQEntity = ((QClient) qEntity).name;
                break;
            case "ru.bitmaster.paymentserver.entity.QOperation":
                qEntityOperation = (QOperation) qEntity;
                break;
            default:
                throw new IllegalStateException("Unexpected value: "
                        + criteryClass.getTypeName());
        }

        List<BooleanExpression> predicates = new ArrayList<>();
        if(name != null && !name.isEmpty()) {
            predicates.add(nameQEntity.containsIgnoreCase(name));
        }
        if(((ACritery) critery).getIds().size()>0) {
            predicates.add(qEntityId.in(((ACritery) critery).getIds()));
        }
        if(clientId !=null) {
            predicates.add(clientQEntity.id.eq(clientId));
        }
        if(clientName !=null) {
            predicates.add(clientQEntity.name.containsIgnoreCase(clientName));
        }
        if (fromDate != null) {
            predicates.add(qEntityOperation.ddate.goe(new java.sql.Date(fromDate.getTime())));
        }
        if (toDate != null) {
            predicates.add(qEntityOperation.ddate.loe(new java.sql.Date(toDate.getTime())));
        }
        if (srcAccountId != null) {
            predicates.add(qEntityOperation.srcAccount.id.eq(srcAccountId));
        }
        if (dstAccountId != null) {
            predicates.add(qEntityOperation.dstAccount.id.eq(dstAccountId));
        }
        BooleanExpression expression = predicates.stream()
                .reduce((predicate,accum) -> accum.and(predicate)).orElse(null);
        return expression;
    }

    public void delete(PK id) {
        getRepository().deleteById(id);
    }

    public List<T> iterableToList(Iterable<T> iterEntities) {
        List<T> entities = StreamSupport.stream(iterEntities.spliterator(), false)
                .collect(Collectors.toList());
        if(entities==null) {
            entities= new ArrayList<T>();
        }
        return entities;
    }

}
