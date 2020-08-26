package ru.bitmaster.paymentserver.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * Класс QOperation - это Querydsl тип для класса Operation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOperation extends EntityPathBase<Operation> {

    private static final long serialVersionUID = 994281195L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOperation operation = new QOperation("operation");

    public final QAEntity _super = new QAEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final DatePath<java.util.Date> ddate = createDate("ddate", java.util.Date.class);

    public final QAccount dstAccount;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QAccount srcAccount;

    public QOperation(String variable) {
        this(Operation.class, forVariable(variable), INITS);
    }

    public QOperation(Path<? extends Operation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOperation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOperation(PathMetadata metadata, PathInits inits) {
        this(Operation.class, metadata, inits);
    }

    public QOperation(Class<? extends Operation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dstAccount = inits.isInitialized("dstAccount") ? new QAccount(forProperty("dstAccount"), inits.get("dstAccount")) : null;
        this.srcAccount = inits.isInitialized("srcAccount") ? new QAccount(forProperty("srcAccount"), inits.get("srcAccount")) : null;
    }

}

