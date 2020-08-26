package ru.bitmaster.paymentserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * Класс QAEntity - это Querydsl тип для класса AEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAEntity extends EntityPathBase<AEntity> {

    private static final long serialVersionUID = 1580107304L;

    public static final QAEntity aEntity = new QAEntity("aEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAEntity(String variable) {
        super(AEntity.class, forVariable(variable));
    }

    public QAEntity(Path<? extends AEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAEntity(PathMetadata metadata) {
        super(AEntity.class, metadata);
    }

}

