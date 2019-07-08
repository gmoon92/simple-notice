package com.moong.notice.domain.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames = {"rule", "uId"})
})
@Getter @Setter @NoArgsConstructor @ToString
public class Member extends BaseEntity{

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private MemberRules rule;
	
	@Column(nullable=false, unique=true, updatable=false)
	private String uId;
	
	@Column(nullable=false)
	private String uPw;

	@Column
	private String name;
	
	/**
	 * @OneToMany 기본 패치전략은 LAZY
	 **/
	@OneToMany(mappedBy="member", fetch=FetchType.EAGER) // 양방향 주체 설정
	@JsonIgnore // 역참조가 될 수 없게 (@JsonBackReference 동일한 효과)
	private List<Board> boards = new ArrayList<Board>();
	
	@Builder
	public Member(Long id, MemberRules rule, String uId, String uPw, String name) {
		this.id = id;
		this.rule = rule;
		this.uId = uId;
		this.uPw = uPw;
		this.name = name;
	}
}