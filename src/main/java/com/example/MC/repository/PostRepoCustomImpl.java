package com.example.MC.repository;

import com.example.MC.constant.BoardType;
import com.example.MC.entity.Post;
import com.example.MC.entity.QPost;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import javax.persistence.EntityManager;

public class PostRepoCustomImpl implements PostRepoCustom{
    private JPAQueryFactory jpaQueryFactory;
    public PostRepoCustomImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<Post> getPostPage(BoardType boardType, Pageable pageable){
        QPost post = QPost.post;
        List<Post> cont = jpaQueryFactory
                .selectFrom(post)
                .where(post.board.eq(boardType))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = jpaQueryFactory
                .select(Wildcard.count)
                .from(post)
                .where(post.board.eq(boardType))
                .fetchOne();
        return new PageImpl<>(cont, pageable, total);
    }
}
