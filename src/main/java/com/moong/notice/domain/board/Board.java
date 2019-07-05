package com.moong.notice.domain.board;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.moong.notice.domain.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter @ToString() @NoArgsConstructor
@AttributeOverrides({
			 @AttributeOverride(name="createdDate", column=@Column(name="CREATE_DATE"))
			,@AttributeOverride(name="modifiedDate", column=@Column(name="MOD_DATE"))
		})
public class Board extends BaseEntity 
				   implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 기본키생성 전략 defualt AUTO
	 **/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, updatable=false)
	@Enumerated(EnumType.STRING)
	private BoardType type;
	
	@Column
	private String title;
	
	@Column
	private StringBuffer contents;

	@Builder
	public Board(BoardType type, String title, StringBuffer contents) {
		this.type = type;
		this.title = title;
		this.contents = contents;
	}
	
	@Override
	public LocalDateTime getCreatedDate() {
		return super.getCreatedDate();
	}
	
	@Override
	public LocalDateTime getModifiedDate() {
		return super.getModifiedDate();
	}
	
	
}
