# SpringBoot와 Vue를 이용한 웹사이트 제작
> 스프링부트와 Vue이용한 웹사이트 제작 OAuth 2.0를 활용하여 Authorization Server, Resource Server를 구축한다
> JWT으로 토큰을 발급하고 Password Grant Type을 이용한다.
> SpringBoot는 모든 기능을 테스트 코드 작성을 하도록 노력한다.

## 작업환경
#### SpringBoot
 - Java 1.8
 - SpringBoot 2.2.2 RELEASE
 - Gradle Project
 - JPA
#### Vue
 - Node
 - Vue
 - Vuex
---
## 구현 순서
#### SpringBoot
1. OAuth 2.0 서버 구축
    - 기본적인 회원가입 기능을 구현
    - Authorization Server 구축
    - Resource Server 구축
      1. hateoas 의존성 추가
      1. Board 도메인 로직 추가
      1. BoardRepository, Service, Controller 추가
      1. ResourceServerConfig 추가
      1. ResourceServer 동작 확인 테스트 추가
#### Vue
1. 회원가입, 로그인 기능 구현
    - 회원가입 페이지 구현
    - 회원가입 기능 구현
    - 로그인 페이지 구현
    - 로그인 기능 구현
