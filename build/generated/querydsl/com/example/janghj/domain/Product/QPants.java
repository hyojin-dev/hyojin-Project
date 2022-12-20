package com.example.janghj.domain.Product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPants is a Querydsl query type for Pants
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPants extends EntityPathBase<Pants> {

    private static final long serialVersionUID = -1611228162L;

    public static final QPants pants = new QPants("pants");

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

    public final NumberPath<Integer> pantsSize = createNumber("pantsSize", Integer.class);

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final EnumPath<ProductColor> productColor = _super.productColor;

    //inherited
    public final NumberPath<Integer> stockQuantity = _super.stockQuantity;

    //inherited
    public final ListPath<com.example.janghj.domain.User.UserCart, com.example.janghj.domain.User.QUserCart> userCart = _super.userCart;

    public QPants(String variable) {
        super(Pants.class, forVariable(variable));
    }

    public QPants(Path<? extends Pants> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPants(PathMetadata metadata) {
        super(Pants.class, metadata);
    }

}

