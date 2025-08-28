


# HeyFY Android App


## 📱 프로젝트 개요

HeyFY는 한국에 있는 외국인 유학생이 한국 대학에 쉽게 정착할 수 있도록 도움을 주는 온보딩 서비스힙니다.

## ✨ 주요 기능

### 🔐 **보안 및 인증**
- JWT 토큰 기반 사용자 인증
- PIN 번호 및 SID 다중 인증
- 자동 토큰 갱신 및 만료 처리

### 💰 **송금 서비스**
- 국내/해외 송금 기능
- 실시간 계좌 잔액 확인
- PIN 번호 인증을 통한 보안 강화
- 거래 내역 조회 및 관리

### 💱 **환전 서비스**
- USD/KRW 실시간 환전
- AI 기반 환율 변동 예측
- 히스토리 차트 및 분석
- 최적 환전 타이밍 알림

### 🏦 **계좌 관리**
- 계좌 등록 및 인증
- 거래 내역 조회

### 🏠 **홈 대시보드**
- 학생증
- 국내 계좌, 외환 계좌 정보 조회
- 1 대 1 멘토링, 동아리 추천, 오늘의 캠퍼스 꿀팀

  ### **외국인 등록증 화면**
  - 외국인 등록증
  - 비자 관련 정보
  - 비자에 따른 일자리 추천

  ### ETC.
- FCM 푸시 알림

  ## 모듈 구조
<img width="29576" height="18108" alt="Frame 8" src="https://github.com/user-attachments/assets/988035bf-5a59-4106-9b79-e04b8d404bbb" />


## 🏗️ 기술 스택
- **Kotlin**
- **Jetpack Compose**
- **MVVM**
- **Clean Architecture**
- **Retrofit**
- - **Hilt** - Android용 DI 프레임워크
- **Coroutines** - 비동기 프로그래밍
- **FCM** - Firebase Cloud Messaging

## 📁 프로젝트 구조

```
HeyFY-Android/
├── app/                           # 메인 애플리케이션 모듈
├── common/                        # 공통 모듈
│   ├── data_store/               # 데이터 저장소
│   ├── manager/                  # 매니저 클래스들
│   ├── text/                     # 텍스트 포맷팅
│   ├── theme/                    # 테마 및 스타일
│   └── utils/                    # 유틸리티 함수들
├── data/                         # 데이터 모듈들
│   ├── account/                  # 계좌 관련 API
│   ├── exchange/                 # 환전 관련 API
│   ├── fcm/                      # FCM 토큰 관리
│   ├── finance/                  # 금융 정보 API
│   ├── home/                     # 홈 데이터 API
│   ├── login/                    # 로그인 관련 API
│   └── transfer/                 # 송금 관련 API
├── feature/                      # 기능 모듈들
│   ├── account/                  # 계좌 관리 화면
│   ├── exchange/                 # 환전 화면
│   ├── finance/                  # 금융 정보 화면
│   ├── home/                     # 홈 화면
│   ├── id/                       # 신분증 인증 화면
│   ├── login/                    # 로그인 화면
│   ├── send_money/               # 송금 화면
│   ├── splash/                   # 스플래시 화면
│   ├── success/                  # 성공 화면
│   ├── tips/                     # 팁 화면
│   └── transaction/              # 거래 내역 화면
├── network/                      # 네트워크 모듈
│   ├── api/                      # API 인터페이스
│   ├── interceptor/              # HTTP 인터셉터
│   ├── model/                    # 네트워크 모델
│   └── utils/                    # 네트워크 유틸리티
└── navigation/                   # 네비게이션 모듈
```

