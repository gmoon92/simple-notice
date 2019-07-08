package com.moong.notice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;

public interface BoardRepository extends JpaRepository<Board, Long>{
	
	Page<Board> findByTypeAndTitleContains(BoardType type, String keyword, Pageable pageable);
	
	
	@Transactional
	@Modifying
	@Query(value="update BOARD b "
				+ "set b.title    = :#{#board.title} "
				+ "   ,b.contents = :#{#board.contents} "
				+ "where b.id = :id", nativeQuery=true)
	Integer updateBoard(@Param("id") Long id, @Param("board") Board board);
	
	
	@Query("select b.id from Board b")
	List<Long> findAllIdQuery();
	
	@Transactional
	@Modifying
	@Query(value="delete from BOARD b where b.id in :ids", nativeQuery=true)
	void deleteALLByIdQuery(@Param("ids") List<Long> ids);
}
