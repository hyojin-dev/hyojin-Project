package com.example.janghj.domain.Product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTop is a Querydsl query type for Top
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTop extends EntityPathBase<Top> {

    private static final long serialVersionUID = -1293291017L;

    public static final QTop top = new QTop("top");

    public final QProduct _super = new QProduct(this);

    //inherited
    public final EnumPath<com.example.janghj.domain.Category> category = _super.category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final EnumPath<ProductColor> productColor = _super.productColor;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    public final NumberPath<Integer> topSize = createNumber("topSize", Integer.class);

    //inherited
    public final ListPath<com.example.janghj.domain.User.UserCart, com.example.janghj.domain.User.QUserCart> userCart = _super.userCart;

    public QTop(String variable) {
        super(Top.class, forVariable(variable));
    }

    public QTop(Path<? extends Top> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTop(PathMetadata metadata) {
        super(Top.class, metadata);
    }

}

