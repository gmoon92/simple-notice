package com.moong.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moong.notice.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
