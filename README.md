# itbook-shop

# 서비스 소개
클라우드 서비스를 적용한 책 쇼핑몰 서비스

# 서비스 플로우
## ci/cd 아키텍쳐
![CI-CD](https://user-images.githubusercontent.com/97177686/220620249-b22b2ae2-c8e0-497f-9ad3-828f43b3cebd.jpg)

## 서비스 구조
![잇북 아키텍처](https://user-images.githubusercontent.com/97177686/220620303-8926e17b-96dc-4642-9000-91c31eaa07f7.png)

## erd
![itbook-shop-v0 0 3 (1)](https://user-images.githubusercontent.com/97177686/220506308-b95b045c-9fa4-42d6-ad9d-42881cadfa48.png)

## 칸반보드
<img width="1512" alt="image" src="https://user-images.githubusercontent.com/88138317/221077344-213b20ce-7c5c-4d10-8355-6dda791fbc63.png">
<img width="1512" alt="image" src="https://user-images.githubusercontent.com/88138317/221077430-141fc63e-f5a7-4660-a0a3-6275038a0739.png">
<img width="1512" alt="image" src="https://user-images.githubusercontent.com/88138317/221077650-26e996e8-309d-44b6-aae6-0edf42af5352.png">

<img width="1277" alt="스크린샷 2023-02-22 오전 11 32 00" src="https://user-images.githubusercontent.com/97177686/220506047-1a240f62-b604-4710-a60e-bedd15dd9893.png">

## WBS
<img width="878" alt="스크린샷 2023-02-24 오전 11 34 03" src="https://user-images.githubusercontent.com/45487307/221078047-288ca762-775b-4f41-9f07-04ef8729b023.png">

## 테스트 커버리지
<img width="993" alt="스크린샷 2023-02-26 오후 9 15 54" src="https://user-images.githubusercontent.com/45487307/221409924-8b4015f3-c0ba-497a-8641-8979856aad16.png">


# 주요기능
## 인프라
* 깃허브 액션 - front, shop, batch, gateway, delivery 서버 CI/CD 관리
* 젠킨스 - auth 서버 CI/CD 관리
* 프론트 서버의 Nginx 웹서버 설치 및 L4 적용

## 인증
* 담당자: 강명관
* 자사 로그인 / 로그아웃
* 소결 로그인 / 로그아웃

## 회원
* 담당자: 노수연
* 회원 조회
* 회원 수정, 삭제
* 자사 회원가입
* 소셜 회원가입
* 배송지
* 유저 API 회원 정보 조회 및 수정
* 관리자 조회 및 상태 수정

## 카테고리
* 담당자: 최겸준
* 카테고리 노출 순서 수정
* 카테고리 조회
* 카테고리 등록, 수정, 삭제

## 상품
   #### 상품
   * 담당자: 이하늬
   * 상품 등록, 수정, 삭제
   * 조회수
   * 상품 추천
   * 상품 판매현황
   * 연관상품 등록 수정 삭제
   * 연관상품 조회
   
   #### 검색
   * 담당자: 송다혜
   * 상품 검색
   
## 즐겨찾기
* 담당자: 강명관
* 즐겨찾기 상품 등록, 수정 삭제
* 즐겨찾기 조회

## 포인트
* 담당자: 최겸준
* 포인트 적립 / 사용 내역 조회
* 포인트 충전
* 포인트 수정
* 포인트 선물

## 쿠폰
* 담당자: 송다혜
* 쿠폰 등록
* 일반 쿠폰
* 웰컴 쿠폰
* 이달의 쿠폰 - 예약형
* 이달의 쿠폰 - 등급형
* 생일 쿠폰
* 쿠폰 조회

## 장바구니
* 담당자: 강명관
* 장바구니 담기
* 장바구니 빼기

## 주문
* 담당자: 정재원, 강명관, 최겸준
* 주문 등록
   - 로그인한 회원 주문 기능
      + 비회원 주문 기능
   - 일반 주문 흐름 구현
      + 장바구니 담기 - 주문서 작성 - 결제
      + 주문서 작성 - 결제
   - 정기 구독 주문 흐름 구현
      + 장바구니 담기 - 주문서 작성 - 결제
      + 주문서 작성 - 결제
   - 포인트, 쿠폰 검사 및 사용 처리

* 주문 취소
   - 주문 상태에 따른 주문 상태 변경 기능
      + 결제완료, 배송대기, 배송완료 상태에서 환불 가능
   - 쿠폰, 포인트 복구
* 주문 조회
* 주문 - 결제 연동

## 정기구독
* 담당자: 정재원
* 구독 등록
   - 해당 상품의 정기구독 가능 여부에 따라서 결정
   - 6개월 1년 단위 신청 기능 및 취소 전까지 배송
* 구독 조회
* 구독 알림

## 결제
* 담당자: 이하늬
* 신용카드 결제 취소
* 신용카드 결제
* 신용카드 환불

## 배송
* 담당자: 정재원
* 배송 생성, 수정, 삭제
* 배송 조회
* 배송 접수 날짜 예약
* 배송 더미 서버 구현
   - 배송 등록, 수정, 삭제

## 리뷰
* 담당자: 노수연
* 리뷰 등록, 수정, 삭제
* 리뷰조회

## 상품 문의
* 담당자: 노수연
* 상품 문의 등록, 수정, 삭제
* 상품 문의 조회
* 작가와 어드민은 상품 문의 답글 등록

# 사용 기술
<img src="https://img.shields.io/badge/Apache Maven-C71A36?style=for-the-badge&logo=Apache Maven&logoColor=white">

<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black"><img src="https://img.shields.io/badge/NGINX-009639?style=for-the-badge&logo=NGINX&logoColor=white">

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"><img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"><img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JWT&logoColor=white">

<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white"><img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"><img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"><img src="https://img.shields.io/badge/SonarLint-CB2029?style=for-the-badge&logo=SonarLint&logoColor=white"><img src="https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=SonarQube&logoColor=white">

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"><img src="https://img.shields.io/badge/Redis-181717?style=for-the-badge&logo=Redis&logoColor=white"><img src="https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white">

<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"><img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"><img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white">

<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"><img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white">

<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">


# 멤버
<a href="https://github.com/itbook-store/itbook-shop/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=itbook-store/itbook-shop" />
</a>
