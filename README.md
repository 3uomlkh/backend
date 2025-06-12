# 💻 Spring Boot 기반 JWT 인증/인가 프로젝트
> Spring Security와 JWT(Json Web Token)를 기반으로 한 인증 및 인가 기능을 구현한 프로젝트입니다.  
> Swagger UI를 통해 API 명세를 시각화하고, 실제 AWS EC2 환경에 배포하여 외부에서도 테스트가 가능하도록 구성하였습니다.

## 🛠️ 기술 스택
- Java 17
- Spring Boot 3.5.0
- Spring Security
- JWT (JSON Web Token)
- H2 Database
- Gradle
- AWS EC2
- Swagger

## 📁 프로젝트 구조

```
└── src
    ├── main
    │   ├── java
    │   │   └── com.example.backend
    │   │       ├── domain
    │   │       │   ├── auth
    │   │       │   │   ├── controller       # 회원가입/로그인 API
    │   │       │   │   ├── dto              # 요청/응답 DTO
    │   │       │   │   └── service          # Auth 비즈니스 로직
    │   │       │   └── user
    │   │       │       ├── controller       # 관리자 권한 부여 API
    │   │       │       ├── dto              # 요청/응답 DTO
    │   │       │       ├── entity           # User 엔티티
    │   │       │       ├── enums            # 권한(Role) 정의
    │   │       │       ├── repository       # JPA Repository
    │   │       │       └── service          # User 비즈니스 로직
    │   │       └── global
    │   │           ├── config               # Spring Security 설정
    │   │           ├── controller           # SwaggerRedirectController
    │   │           ├── dto                  # 공통 에러 응답 DTO
    │   │           ├── exception            # 커스텀 예외 및 핸들러
    │   │           ├── filter               # JWT 인증 필터
    │   │           └── util                 # JWT 유틸리티
    │   └── resources
    │       └── application.yml              # 환경 변수 설정
    └── test
        └── java
            └── com.example.backend
                ├── domain.auth              # AuthServiceTest
                ├── domain.user              # UserServiceTest, UserControllerTest
                └── BackendApplicationTests  
```

## 🚀 기능 요약
- 회원가입 (`/api/auth/signup`)
- 로그인 (`/api/auth/signin`)
- JWT 생성 및 검증
- 인가 처리 (권한별 접근 제어)
- Swagger UI 연동
- AWS EC2 배포

## 🔎 API 명세

- **Swagger UI**  
  🔗 [http://13.125.248.4:8080/swagger](http://13.125.248.4:8080/swagger)

- **API 엔드포인트**  
  🔗 [http://13.125.248.4:8080](http://13.125.248.4:8080)

- `Authorization: Bearer <token>` 형식으로 인증 필요