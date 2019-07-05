package com.moong.notice.domain.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.moong.notice.domain.common.BaseEntity;

import lombok.Getter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Member extends BaseEntity{

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String uId;
	
	@Column
	private String uPw;
	
	
	
	
}
