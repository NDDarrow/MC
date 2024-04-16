package com.example.MC.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAPost is a Querydsl query type for APost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAPost extends EntityPathBase<APost> {

    private static final long serialVersionUID = 1865996829L;

    public static final QAPost aPost = new QAPost("aPost");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> bad = createNumber("bad", Integer.class);

    public final EnumPath<com.example.MC.constant.BoardType> board = createEnum("board", com.example.MC.constant.BoardType.class);

    public final StringPath body = createString("body");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Integer> good = createNumber("good", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final NumberPath<Integer> password = createNumber("password", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QAPost(String variable) {
        super(APost.class, forVariable(variable));
    }

    public QAPost(Path<? extends APost> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAPost(PathMetadata metadata) {
        super(APost.class, metadata);
    }

}

