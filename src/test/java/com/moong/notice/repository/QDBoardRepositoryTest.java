package com.moong.notice.repository;

import com.moong.notice.config.JPAConfig;
import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.domain.board.QBoard;
import com.moong.notice.service.dto.SearchParam;
import com.moong.notice.service.dto.SearchParam2;
import com.moong.notice.service.dto.SelectOptions;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
@Import(JPAConfig.class)
@DataJpaTest
@Transactional
public class QDBoardRepositoryTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private JPAQueryFactory jpaQueryFactory;

    private SearchParam2 params;

    @Before
    public void setUp() {
        params = new SearchParam2();
        params.setType(BoardType.NOTICE);
        params.setOptionKeyword(SelectOptions.TITLE);
        params.setOptionDate(SelectOptions.CREATED_DATE);
        params.setStaYmd( SearchParam.toLocalDateTime("2019-01-01") );
        params.setEndYmd( SearchParam.toLocalDateTime("2019-12-30") );
        params.setKeyword("test");

        boardRepository.saveAll(
                Arrays.asList(
                        Board.builder()
                                .title("test2")
                                .contents("테스트 용 내용")
                                .type(BoardType.NOTICE)
                                .build()
                        ,Board.builder()
                                .title("test2")
                                .contents("테스트 용 내용")
                                .type(BoardType.NOTICE)
                                .build()
                )
        );
    }

// switch 문을 Optional로 대체할 수 있을까?
        @Test
        public void isOptionalNullTest(){
//        switch (params.getOptionKeyword()) {
//            case WRITER     : bb.and( board.member.name.contains(keyword) );     break;
//            case TITLE      : bb.and( board.title.contains(keyword) );           break;
//            case CONTENTS   : bb.and( board.contents.contains(keyword) );        break;
//            default:
//            if (StringUtils.isEmpty(keyword)) {
////                return null;
//            } else {
//                throw new RuntimeException("선택된 키워드가 없습니다.");
//            }
//        }
            final BooleanBuilder bb = new BooleanBuilder();
            final String keyword = params.getKeyword();

            /*
            * if/else 또는 switch 문 같은 논리 문법을 foreach로 해결
            * */
            Optional optOptions = Optional.of(params.getOptionKeyword());
            System.err.println(
                            optOptions
                                .filter(option -> option.equals(SelectOptions.TITLE))
                                .map( option -> bb.and(QBoard.board.member.name.contains(keyword)) )

//                    .orElseGet(() -> )
//                    .ifPresent(option -> System.out.println(option.getOption()) )
//                    .
//                    .orElseThrow( () -> new RuntimeException("선택된 키워드가 없습니다.") )



            );
    }

    @Test
    public void isQueryDSL() {
        System.err.println("=================");
        System.err.println("=================");
        System.err.println(boardRepository.findAll());

//        JPAQuery<Board> query = new JPAQuery(em);
        JPAQuery<Board> query = jpaQueryFactory.selectFrom(board);

        List<Board> boards = query //.from(board)// 기본 인스턴스 활용
                .where(
                         getSearchCondition(params)
                )
                .orderBy(board.id.asc())
                .fetch(); // list(board)

        System.err.println("=================");
        System.err.println("=================");
        System.err.println("=================");
        System.err.println(boards);
    }

    private BooleanBuilder getSearchCondition(final SearchParam2 params) {
//        CaseBuilder cb = new CaseBuilder();
        final BooleanBuilder bb = new BooleanBuilder();

        if(params.getType() != null) {
           bb.and(board.type.eq(params.getType()));
        }

        final String        keyword     = params.getKeyword();
        final LocalDateTime staYmd      = params.getStaYmd();
        final LocalDateTime endYmd      = params.getEndYmd();


        switch (params.getOptionKeyword()) {
            case WRITER     : bb.and( board.member.name.contains(keyword) );     break;
            case TITLE      : bb.and( board.title.contains(keyword) );           break;
            case CONTENTS   : bb.and( board.contents.contains(keyword) );        break;
            default:
                return validPrarms(keyword);
        }

        switch (params.getOptionDate()) {
            case CREATED_DATE: bb.and( board.createdDate.between(staYmd, endYmd) );     break;
            case MODIFED_DATE: bb.and( board.modifiedDate.between(staYmd, endYmd) );    break;
            default:
                return validPrarms(keyword);
        }
        return bb;
    }

    private BooleanBuilder validPrarms(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return null;
        } else {
            throw new RuntimeException("선택된 키워드가 없습니다.");
        }
    }


    /*
    * if문을 개선할 순 없을까 ?
    * */



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

//        Predicate<SelectOptions> condition1 = keywordOptions::contains;
}
