package com.example.janghj.domain.User;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCart is a Querydsl query type for UserCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCart extends EntityPathBase<UserCart> {

    private static final long serialVersionUID = 952562287L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCart userCart = new QUserCart("userCart");

    public final com.example.janghj.domain.QTimestamped _super = new com.example.janghj.domain.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath likeIt = createBoolean("likeIt");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final QUser user;

    public QUserCart(String variable) {
        this(UserCart.class, forVariable(variable), INITS);
    }

    public QUserCart(Path<? extends UserCart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCart(PathMetadata metadata, PathInits inits) {
        this(UserCart.class, metadata, inits);
    }

    public QUserCart(Class<? extends UserCart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

