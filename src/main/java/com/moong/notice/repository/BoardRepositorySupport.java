package com.moong.notice.repository;

import com.moong.notice.domain.board.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moong.notice.domain.board.QBoard.board;

/**
 * Created by gmun0929.work@gmail.com
 * Blog : https://gmun.github.io
 * Github : https://github.com/gmun
 *
 * @author : Moon Gyeom(moong)
 * @since : 2019-08-03
 */
@Repository
public class BoardRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Board.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Board> findByName(String test){
        return jpaQueryFactory.selectFrom(board)
                              .fetch();
    }
}
