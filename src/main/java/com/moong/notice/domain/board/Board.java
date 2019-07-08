package com.moong.notice.domain.board;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.moong.notice.domain.common.BaseEntity;
import com.moong.notice.domain.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter @NoArgsConstructor @ToString(exclude="member")
@AttributeOverrides({
			 @AttributeOverride(name="createdDate", column=@Column(name="CREATE_DATE"))
			,@AttributeOverride(name="modifiedDate", column=@Column(name="MOD_DATE"))
		})
public class Board extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, updatable=false)
	@Enumerated(EnumType.STRING)
	private BoardType type;
	
	@Column
	private String title;
	
	@Column(length=4000)
	private String contents;
	
	/**
	* @ManyToOne는 기본적인 패치 전략은 EAGER
	* 게시판 - 회원의 관계 N:1
	* 
	* 무한재귀 발생....
	* @JsonIgnore, @JsonBackReference, @JsonManagedReference로 
	* 재귀 흐름을 끊을순 있지만, 순한 참조로 단방향만 진행
	* @JsonIdentityInfo는 ... deserialize에서 문제...
	* 
	* 양방향 참조는 결과적으로 JSON에 위배
	**/
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	

	@Builder
	public Board(Long id, BoardType type, String title, String contents) {
		this.id = id;
		this.type = type;
		this.title = title;
		this.contents = contents;
	}
	
	// 양방향 설정
	public Board setMember(Member member) {
		if(member != null){
			member.getBoards().remove(this);
			// 존재하면 리스트에서 제거
		}
		this.member = member;
		this.member.getBoards().add(this);
		return this;
	}
}
