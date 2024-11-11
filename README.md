# Code Sympony 
Code Sympony는 WebSocket과 Github API를 활용하여,
유저가 코드 리뷰와 채팅을 같이 할 수 있게 하여
개발의 생산성을 올려주는 협업 SaaS 플랫폼입니다.
2025년 2월 졸업 예정이며, 유한대학교 졸업 작품입니다.

## 프로젝트 중점사항
* Fake 객체를 통해 유닛 테스트를 DB의 개입 없이 실행하기
* @DataJpaTest를 통해 필요한 쿼리를 서비스의 개입 없이 독립적으로 테스트하기
* Request 객체에 대한 Validate와 Exception Handling으로 협업에 적합한 인터페이스 제공
* Spring Security를 Session 형태로 제공
* Redis와 WebSocket을 통해 알림 및 처리 기능을 SSR 렌더링 클라이언트에서 제공
* N:M 매핑을 중간 테이블로 해소하고, 그 과정에서 정보를 가져올때에 발생하는 1:N 문제를 Fetch Join으로 해결
* Github Log Commit Graph의 구조 전체를 캐싱하여, 응답시간 최소화

## 프로젝트 구조도


![CodeSympony-ProjectStructure](https://github.com/user-attachments/assets/bb49040e-5e8d-4bfd-8338-82c2a68c9405)

## DB ERD


![CodeSympony-ERD](https://github.com/user-attachments/assets/fc682f72-4981-4712-b9f5-b9b6394d7ab8)


## 주요 기능

* 회원가입 및 로그인 (스프링 시큐리티)
* 회원 정보 수정
* 방 만들기
* 방에 초대하기
* 채팅하기
* 채팅 내역 스케줄링하여 저장
* Github API를 호출하여 Branch, Owner에 따라, 커밋 내용, 파일 내용, 파일 변경 사항 등을 화면에 표기
* 신규 채팅 알림 기능 설정
* 유저가 읽지 않은 메세지수 동기화 기능 구현

## 개인적으로 잘 했다 생각하는 점

추후 블로그 글로 추가 예정

***

## Page image

### Sign up Page
![signup-page](https://github.com/user-attachments/assets/4f8b0a5b-71f8-47a0-a826-ea0458d8b573)

### Login Page
![loginpage](https://github.com/user-attachments/assets/da579a90-b434-4f66-aa7e-5e161c4de29a)

### Dashboard

![dashboard](https://github.com/user-attachments/assets/c52d9360-91bf-4024-a847-0b4138c34cf6)

### Modify Page
![modifypage](https://github.com/user-attachments/assets/1633be95-7fb1-40d5-8e8b-79d2d79cae2d)


