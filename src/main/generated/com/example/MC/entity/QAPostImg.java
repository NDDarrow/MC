package com.example.MC.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAPostImg is a Querydsl query type for APostImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAPostImg extends EntityPathBase<APostImg> {

    private static final long serialVersionUID = 149894246L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAPostImg aPostImg = new QAPostImg("aPostImg");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAPost APost;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath oriImgName = createString("oriImgName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QAPostImg(String variable) {
        this(APostImg.class, forVariable(variable), INITS);
    }

    public QAPostImg(Path<? extends APostImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAPostImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAPostImg(PathMetadata metadata, PathInits inits) {
        this(APostImg.class, metadata, inits);
    }

    public QAPostImg(Class<? extends APostImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.APost = inits.isInitialized("APost") ? new QAPost(forProperty("APost")) : null;
    }

}

