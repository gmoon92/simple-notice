package com.moong.notice.domain.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.ToString;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
public abstract class BaseEntity {

	@CreatedDate
	@Column(updatable=false)
	private LocalDateTime createdDate; // 생성일
	
	@LastModifiedDate
	private LocalDateTime modifiedDate; // 수정일자
}
