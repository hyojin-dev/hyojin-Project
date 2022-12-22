package com.example.janghj.domain.Product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1714889583L;

    public static final QProduct product = new QProduct("product");

    public final com.example.janghj.domain.QTimestamped _super = new com.example.janghj.domain.QTimestamped(this);

    public final EnumPath<com.example.janghj.domain.Category> category = createEnum("category", com.example.janghj.domain.Category.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final ListPath<com.example.janghj.domain.OrderProduct, com.example.janghj.domain.QOrderProduct> orderProduct = this.<com.example.janghj.domain.OrderProduct, com.example.janghj.domain.QOrderProduct>createList("orderProduct", com.example.janghj.domain.OrderProduct.class, com.example.janghj.domain.QOrderProduct.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final EnumPath<ProductColor> productColor = createEnum("productColor", ProductColor.class);

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public final ListPath<com.example.janghj.domain.User.UserCart, com.example.janghj.domain.User.QUserCart> userCart = this.<com.example.janghj.domain.User.UserCart, com.example.janghj.domain.User.QUserCart>createList("userCart", com.example.janghj.domain.User.UserCart.class, com.example.janghj.domain.User.QUserCart.class, PathInits.DIRECT2);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

