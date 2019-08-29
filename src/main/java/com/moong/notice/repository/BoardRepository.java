package com.moong.notice.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.service.dto.SearchParam;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{

	@Query(value="SELECT b.* "
			+ "	    FROM board b"
			+ "	    	INNER JOIN member m ON b.member_id = m.id"
			+ "     WHERE b.type = :#{#params.type} "
			+ "       AND CASE :#{#params.option_keyword}"
			+ "     	       WHEN 'WRITER'   THEN m.name LIKE CONCAT('%', :#{#params.keyword}, '%')"
			+ "     	       WHEN 'TITLE'    THEN b.title LIKE CONCAT('%', :#{#params.keyword}, '%')"
			+ "     	       WHEN 'CONTENTS' THEN b.contents LIKE CONCAT('%', :#{#params.keyword}, '%')"
			+ "     	       END "
			+ "       AND CASE :#{#params.option_date} "
			+ "     		   WHEN 'CREATED_DATE' 		THEN	b.create_date BETWEEN :#{#params.sta_ymd} AND :#{#params.end_ymd} "
			+ "     		   WHEN 'MODIFED_DATE' 		THEN	b.mod_date BETWEEN :#{#params.sta_ymd} AND :#{#params.end_ymd} "
			+ "     		   END "
			, nativeQuery=true)
	Page<Board> findSearchQuery(@Param("params") SearchParam params, Pageable pageable);
	
											
	@Transactional
	@Modifying
	@Query(value="update BOARD b "
				+ "set b.title    = :#{#board.title} "
				+ "   ,b.contents = :#{#board.contents} "
				+ "where b.id = :id", nativeQuery=true)
	Integer updateBoard(@Param("id") Long id, @Param("board") Board board);
	
	@Query("select b.id from Board b")
	@Deprecated
	List<Long> findAllIdQuery();

	@Deprecated
	Page<Board> findByTypeAndTitleContains(BoardType type, String title, Pageable pageable);
	
	@Query(value="delete from BOARD b where b.id in :ids", nativeQuery=true)
	@Modifying
	@Deprecated
	void deleteALLByIdQuery(@Param("ids") List<Long> ids);
	
	// 생성일자, 내용 조회
	@Deprecated
	Page<Board> findByTypeAndContentsContainsAndCreatedDateBetween(BoardType type
			, String keyword
			, LocalDateTime sta, LocalDateTime end, Pageable pageable);

	// 생성일자, 제목 조회
	Page<Board> findByTypeAndTitleContainsAndCreatedDateBetween(BoardType type
			, String keyword
			, LocalDateTime sta, LocalDateTime end, Pageable pageable);

	// 생성일자, 작성자 조회
	@Deprecated
	@Query(value="select b.* "
			+ "	  from BOARD b"
			+ "		  ,MEMBER m "
			+ "where b.member_id = m.id "
			+ "  and b.type = :type "
			+ "  and m.name = :name "
			+ "  and m.created_date between :sta and :end "
			, nativeQuery=true)
	Page<Board> findByTypeAndWriterCreatedDateBetweenQuery(@Param("type")String type
			, @Param("name")String keyword
			, @Param("sta")LocalDateTime sta, @Param("end")LocalDateTime end, Pageable pageable);


	// 수정일자, 내용 조회
	@Deprecated
	Page<Board> findByTypeAndContentsContainsAndModifiedDateBetween(BoardType type, String keyword, LocalDateTime sta, LocalDateTime end, Pageable pageable);

	// 수정일자, 제목 조회
	@Deprecated
	Page<Board> findByTypeAndTitleContainsAndModifiedDateBetween(BoardType type, String keyword, LocalDateTime sta, LocalDateTime end, Pageable pageable);

	// 수정일자, 작성자 조회
	@Deprecated
	@Query(value="select b.* "
			+ "	  from BOARD b"
			+ "		  ,MEMBER m "
			+ "where b.member_id = m.id "
			+ "  and b.type = :type "
			+ "  and m.name = :name "
			+ "  and m.created_date between :sta and :end "
			, nativeQuery=true)
	Page<Board> findByTypeAndWriterModifiedDateBetweenQuery(@Param("type")String type
			, @Param("name")String keyword
			, @Param("sta")LocalDateTime sta, @Param("end")LocalDateTime end, Pageable pageable);
}
