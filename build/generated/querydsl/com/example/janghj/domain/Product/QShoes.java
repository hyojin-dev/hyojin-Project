package com.example.janghj.domain.Product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShoes is a Querydsl query type for Shoes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShoes extends EntityPathBase<Shoes> {

    private static final long serialVersionUID = -1608248566L;

    public static final QShoes shoes = new QShoes("shoes");

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
    public final ListPath<com.example.janghj.domain.OrderProduct, com.example.janghj.domain.QOrderProduct> orderProduct = _super.orderProduct;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final EnumPath<ProductColor> productColor = _super.productColor;

    public final NumberPath<Integer> shoesSize = createNumber("shoesSize", Integer.class);

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    //inherited
    public final ListPath<com.example.janghj.domain.User.UserCart, com.example.janghj.domain.User.QUserCart> userCart = _super.userCart;

    public QShoes(String variable) {
        super(Shoes.class, forVariable(variable));
    }

    public QShoes(Path<? extends Shoes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShoes(PathMetadata metadata) {
        super(Shoes.class, metadata);
    }

}

