package com.example.MC.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostImg is a Querydsl query type for PostImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostImg extends EntityPathBase<PostImg> {

    private static final long serialVersionUID = -754534017L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostImg postImg = new QPostImg("postImg");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath oriImgName = createString("oriImgName");

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QPostImg(String variable) {
        this(PostImg.class, forVariable(variable), INITS);
    }

    public QPostImg(Path<? extends PostImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostImg(PathMetadata metadata, PathInits inits) {
        this(PostImg.class, metadata, inits);
    }

    public QPostImg(Class<? extends PostImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

