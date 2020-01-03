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
 - Vuex@
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
    - Cors 설정
      1. Authorization Server 측에 cors를 설정해야 토큰 발급할 때 문제가 없다.
      2. Filter로 OPTIONS 메서드가 들어올 때 response를 강제할 수 있지만 모든 Domain을 허용해야 하므로 좋지않다.

#### Vue
1. 회원가입, 로그인 기능 구현
    - 회원가입, 로그인 뷰 구현
      - MDBootstrap으로 화면 재구성
      - slot을 이용하여 하나의 Modal을 여러 상황에 맞게 커스텀
    - 회원가입, 로그인 API 통신 구현
      - 회원가입, 로그인 성공 시 자동으로 다음 화면으로 변경
      - 회원가입, 로그인 실패 시 Modal을 띄워 사용자에게 알려줌
      - 로그인 시 state의 tokenInfo에 토큰 정보들을 담아놓고 axios defalut Header에 AccessToken 등록

2. 권한별 라우터 설정, 로그아웃 구현
    - 라우터 내비게이션 가드를 이용하여 인증되지 않은 사용자가 인증이 필요한 페이지 접속시 Modal을 띄우고 로그인 화면으로 리다이렉트
    - 로그인 시 페이지 상단의 로그인, 회원가입을 로그아웃으로 변경
    - 로그아웃 시 token 정보를 초기화하여 인증이 필요한 서버를 접속하기 위해서는 다시 로그인 필요


#### AWS 배포하기
1. **EC2, RDS 생성**
    - 필요한 프로그램 다운로드
    - EC2에서 RDS 접속해보기

2. **EC2에서 직접 서버 실행**
    - 직접 Clone후 Build하여 실행
    - 배포 스크립트 만들어서 실행

3. **배포 자동화**
    - Travis CI 설정
    - AWS S3 생성 후 Travis CI와 연동
    - AWS CodeDeploy 생성 후 Travis CI, S3, CodeDeploy 연동
    - 배포 스크립트 만들어서 배포 자동화

4. **무중단 배포**
    - 엔진엑스 설치하여 프로젝트와 연동하기
    - 스크립트 만들기
    - profiles 설정 후 테스트
    
