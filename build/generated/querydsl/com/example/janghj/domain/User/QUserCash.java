package com.example.janghj.domain.User;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCash is a Querydsl query type for UserCash
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCash extends EntityPathBase<UserCash> {

    private static final long serialVersionUID = 952562306L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCash userCash = new QUserCash("userCash");

    public final com.example.janghj.domain.QTimestamped _super = new com.example.janghj.domain.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> money = createNumber("money", Integer.class);

    public final QUser user;

    public QUserCash(String variable) {
        this(UserCash.class, forVariable(variable), INITS);
    }

    public QUserCash(Path<? extends UserCash> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCash(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCash(PathMetadata metadata, PathInits inits) {
        this(UserCash.class, metadata, inits);
    }

    public QUserCash(Class<? extends UserCash> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

