# Spring boot REST API 게시판 만들기

## 개발 환경
- Java8
- Spring Boot 2.1.6.RELEASE
- JPA, H2
- JUnit(AssertJ), Lombok, Maven

>~~추후 동적 쿼리와 객체 타입에 관련하여 Querydsl을 적용하여 개발할 예정~~

## 구현 기능
- 게시판 CURD(작성, 수정, 조회, 삭제)
- 페이징 처리
  - JPA Pagination  
- 목록 조회(키워드 검색)
- Auditing
- 조회시 제목, 작성일, 작성자, 최종수정일, 내용이 조회 가능하다.

## 프로젝트 실행

Maven -> UpdateProject
Run -> NoticeApplication

포트 설정 9090
- index - http://localhost:9090/
- H2    - http://localhost:9090/h2-console
