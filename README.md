### 프로젝트 개요 및 목표

본 프로젝트 'CodeGram'은 코드 중심의 모바일 소셜 네트워크 서비스 구축을 목표로 합니다. 핵심 목표는 사용자가 모바일 환경에 최적화된 에디터를 통해 코드를 작성하고 공유하는 플랫폼을 제공하는 것입니다.

특히 AI 기반의 보안 검증 시스템을 도입하여 안전한 코드 공유 환경을 조성하고, '챌린지' 기능과 '바이브 서치' 같은 독창적인 기능을 통해 개발자 커뮤니티의 실용적인 지식 교류 및 상호 학습을 활성화하는 것을 최종 목표로 합니다.

### 주요 기능 요구사항 (Functional Requirements)

- **소셜 피드:** 인스타그램 형식의 UI를 통해 코드 게시물을 탐색하고, 좋아요, 댓글, 팔로우 등 소셜 활동을 수행합니다.
- **코드 작성 및 게시:** 모바일 최적화 코드 에디터와 구문 강조 기능을 통해 소스 코드와 설명을 작성합니다.
- **AI 기반 보안 검증:** 코드 게시 전, AI 서버가 실시간으로 악성/악의적 코드를 정적 분석하여 안전성을 검증합니다.
- **바이브 서치 (Vibe Search):** AI가 코드를 분석하여 어울리는 이모지를 자동으로 태그하며, 사용자는 이모지 조합으로 게시물을 검색할 수 있습니다.
- **챌린지:** 코딩 챌린지 문제에 도전하고 다른 사용자의 풀이를 참고하며 학습합니다.
- **코드 실행:** 업로드된 코드를 서버의 안전한 Docker 샌드박스 환경에서 실행하고 결과를 확인합니다.

### 개발환경
| 구분 | 소프트웨어/도구 | 버전/역할 |
| --- | --- | --- |
| 개발 환경 | IDE / Build Tool | Android Studio NarWhal+ / Gradle 8.x |
| OS | Min SDK / Target SDK | Android 8.0 (API 26) / Android 15 (API 35) |
| 언어 | Programming Language | Kotlin 2.x.x 이상 |
| 주요 라이브러리 | UI / Architecture Pattern | Jetpack Compose / MVVM |
|  | Network / DI | Retrofit2, OkHttp / Hilt |
|  | 비동기 처리 | Kotlin Coroutines & Flow |

### 안드로이드 클라이언트 모듈 구조
```
- 📂 app (Application Module)

- 📂 presentation
  - 역할: UI 로직, ViewModel, Composable Screen, 상태 관리

- 📂 domain
  - 역할: 핵심 비즈니스 로직, UseCase, Entity, Repository 인터페이스

- 📂 data
  - 역할: REST API 통신, DTO 변환, Repository 구현체 제공, DTO, RepositoryImpl

- 📂 core
  - 역할: 공통 유틸리티, Base 클래스, DI 설정 등
```

### Application UI
<img width="500" height="250" alt="image" src="https://github.com/user-attachments/assets/20414448-cec9-4505-aecf-795d24bf0bf5" />
