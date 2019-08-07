package com.moong.notice.repository;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.domain.board.QBoard;
import com.moong.notice.service.dto.SearchParam;
import com.moong.notice.service.dto.SelectOptions;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.moong.notice.domain.board.QBoard.board;

/**
 * Created by gmun0929.work@gmail.com
 * Blog : https://gmun.github.io
 * Github : https://github.com/gmun
 *
 * @author : Moon Gyeom(moong)
 * @since : 2019-08-04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QDBoardRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp(){
        em.persist(Board.builder()
                        .title("test")
                        .contents("테스트 용 내용")
                        .type(BoardType.NOTICE)
                        .build()
                    );

        em.persist(Board.builder()
                        .title("test2")
                        .contents("테스트 용 내용")
                        .type(BoardType.NOTICE)
                        .build()
                    );

        em.flush();
    }

    @Transactional
    @Test
    public void isQueryDSL(){
        JPAQuery<Board> query = new JPAQuery(em);
//        [1] 직접 지정
//        QBoard qBoard = new QBoard("m"); // 생성되는 JPQL의 별칭이 m
//        QBoard qBoard = QBoard.board; // 기본 인스턴스 활용
//
//                        WHERE b.type = :#{#params.type} "
//                + "       AND CASE :#{#params.option_keyword}"
//                + "     	       WHEN 'WRITER'   THEN m.name LIKE CONCAT('%', :#{#params.keyword}, '%')"
//                + "     	       WHEN 'TITLE'    THEN b.title LIKE CONCAT('%', :#{#params.keyword}, '%')"
//                + "     	       WHEN 'CONTENTS' THEN b.contents LIKE CONCAT('%', :#{#params.keyword}, '%')"
//                + "     	       END "
//                + "       AND CASE :#{#params.option_date} "
//                + "     		   WHEN 'CREATED_DATE' 		THEN	b.create_date BETWEEN :#{#params.sta_ymd} AND :#{#params.end_ymd} "
//                + "     		   WHEN 'MODIFED_DATE' 		THEN	b.mod_date BETWEEN :#{#params.sta_ymd} AND :#{#params.end_ymd} "
//                + "     		   END "
        SearchParam sp = new SearchParam();
        sp.setOption_keyword(SelectOptions.WRITER.getOption());

        final List<SelectOptions> keywordOptions = Arrays.asList( SelectOptions.TITLE, SelectOptions.CONTENTS, SelectOptions.WRITER );
        final List<SelectOptions> dateOptions    = Arrays.asList( SelectOptions.CREATED_DATE, SelectOptions.MODIFED_DATE );

//        Predicate<SelectOptions> condition1 = keywordOptions::contains;
        SelectOptions option1 = SelectOptions.TITLE;
        SelectOptions option2 = SelectOptions.MODIFED_DATE;
        String keyword = "Test";

        // where 절에 null 올 경우 조건문에서 생략됨
//        BooleanExpression be = opti
//
//        JPAExpressions.
//        Expression<SelectOptions> cases = new CaseBuilder()
////                                                .when(option1.equals(SelectOptions.TITLE))
//                .when(Expressions.asBoolean(option1.equals(SelectOptions.TITLE)))
//                .then(
//                        Expressions.
//                        board.title.contains(keyword)
//                )
//
//                ;

        // case - when - then
//        CaseBuilder cb = new CaseBuilder()
//                                .when(
//                                        board.contents.eq
////                                        Stream.of(option1).anyMatch(option -> keywordOptions.contains(option))
//
//                                );
        List<Board> boards = query.from(board)// 기본 인스턴스 활용
                                  .where(
                                          board.type.eq(BoardType.NOTICE)
                                          .and(
                                            board.title.eq("test")
                                          )

                                  )
                                  .orderBy(board.id.asc())
                                  .fetch(); // list(board)

        System.out.println("=================");
        System.out.println("=================");
        System.out.println("=================");
        System.out.println(boards);
    }
}
