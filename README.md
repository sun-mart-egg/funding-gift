# 생선가게

여러 그룹이 모여 선물을 함께 구매하고 제공하는 서비스

<br>

### ⏰ 개발 기간

- 2024.02.26 - 2024.03.10 기획, 설계
- 2024.03.11 - 2024.04.04 개발

<br>

### 💡 기획 배경

선물을 주고 받는 과정에서 여러 `곤란한 상황`을 마주한 적이 있을 것이다.

- **감정 문제**
    - 선물을 고르면서 상대방이 원하는 선물을 알지 못해 고민하다가 무난한 선물인 치킨, 커피 등을 줬다.
    - 매년 생일 때마다 치킨, 커피 등 같은 선물들을 여러 개 받아서 고맙지만 난감했다.
    - 이렇듯 선물을 주는 사람은 받는 사람이 어떤 선물을 원하는지 모르는 상황에서, 
    받는 사람은 실제 자신이 필요한 선물을 받지 못해 찝찝한 감정을 느낀다.
- **예산 문제**
    - 원하는 선물이 있어도, 부담스러운 금액 때문에 말하기 곤란한 상황이 생긴다.
    - 원하는 선물을 알고 있어도, 한정된 예산 때문에 원하는 선물을 주지 못하는 경우가 발생한다.

따라서 `모두가 기쁜 마음으로 예산 걱정 없이 선물을 주고 받을 수 있도록` 하기 위해 해당 서비스를 기획하게 되었다.

<br>

### 🔑 목표

1. 선물을 주고받는 과정에서 발생하는 `감정 문제를 해결`한다.
    1. 어떤 선물을 줄지 고민하는 과정에서 오는 스트레스를 해결하여 선물 자체에 집중할 수 있도록 한다.
    2. 받고 싶은 선물을 부담없이 친구들에게 알려 자신의 취향과 필요에 맞는 선물을 받을 수 있도록 한다.
2. 선물을 주고받는 과정에서 발생하는 `예산 문제를 해결`한다.
    1. 사람들에게 금전적 부담을 주지 않으면서 자신이 원하는 선물을 요청할 수 있도록 한다.
    2. 여러 사람들이 함께 선물을 제공함으로써 동일한 금액 대비 더 효율적인 선물을 줄 수 있도록 한다. 

<br>

### 🎁 주요 기능

1. **알림**
    - 생일이 얼마 안 남은 사람에게 `선물 등록 생성 알림` 을 보낸다.
        - ex) ‘곧 생일이 다가 와요. ooo님! 펀딩을 만들어보세요.’
    - 선물을 받을 사람을 친한 친구로 등록한 사람들에게 `친구의 펀딩 등록 알림` 을 보낸다.
        - ex) ‘ooo님이 펀딩을 등록했어요!’
    - 친구가 내 펀딩에 참여하면 `펀딩 참여 알림` 을 보낸다.
        - ex) ‘ooo님이 펀딩에 참여했어요!’
2. **선물 등록**
    - 자신이 받고 싶은 `상품`을 골라 등록한다.
    - `이벤트 명, 이벤트 날짜, 펀딩 기간(최대 7일), 최소 금액`을 설정한다.
3. **선물 펀딩**
    - 카카오톡 친구가 되어 있는 사용자들은 모두 펀딩에 참여할 수 있다.
    - 사용자들은 등록된 선물에 자신이 `원하는 금액`을 내면서 친구에게 `축하 메시지`를 남길 수 있다.
    - 목표 금액에 도달하기까지 `모인 금액을 표시`하여 모든 사용자들이 펀딩 진행 상황을 확인할 수 있다.
    - 각 사용자가 낸 정확한 금액은 표시되지 않는다.

<br>

### 🧸 서비스 화면
1. 메인 화면

    <img width="150" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/048ba82e-b634-4450-ab4d-b949c2cf484ag">
    
    - 배너
    - 펀딩 만들기, 참여하기
    - 추천 상품 조회

2. 상품 목록 조회

    <img width="450" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/c04527a2-ad61-4c8e-b27d-b22831f25825">

    - 카테고리 별 조회
    - 정렬 조건 필터링
    - 키워드 검색

3. 상품 상세 조회

    <img width="300" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/9fcf5537-f8bc-4b61-964f-63c3ca2c2ce8">

    - 상품 이미지, 이름, 옵션, 설명, 리뷰

4. 펀딩 만들기

    <img width="450" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/3b4d4902-5b55-42c3-b66f-22aefc6ef18f">
    
    - 펀딩 정보 입력

5. 친구 펀딩 피드, 스토리 보기

    <img width="450" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/8c9f42a8-02b6-4e7e-abf8-6ad037c41681">
    
    - 친구의 진행중인 펀딩 정보

6. 펀딩 참여

    <img width="450" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/3a25dcef-6f07-4429-b6bb-becb650e7ac5">

    - 축하 메시지 보내기, 결제

7. 캘린더

    <img width="150" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/b94376bc-dbfb-427c-babf-af16e58313ed">

    - 친구들의 기념일 모아보기

8. 위시리스트

    <img width="150" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/19464971-02be-4783-a44f-19aef089dc3f">

    - 위시리스트 추가, 삭제, 조회

9. 내 프로필

    <img width="150" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/79428587-774e-4d91-b7f8-d80b8bb7a509">

    - 내가 만든 펀딩, 내가 참여한 펀딩

10. 친구 프로필

    <img width="150" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/fc6c1974-7173-484a-b070-62076db286ef">

    - 친구가 만든 펀딩

11. 친구 목록

    <img width="150" alt="image" src="https://github.com/sun-mart-egg/funding-gift/assets/66028419/76b06e03-d361-465a-8735-65455a7e06ad">

    - 카카오톡 친구 연동


<br>

### 🔌 기술 스택

- Frontend
    - React
    - Javascript
    - PWA
    - Zustand
- Backend
    - Java 17
    - Spring Boot 3.2.3
    - Spring Data JPA
    - Spring Web
    - Spring Validation
    - Spring Security
    - oauth2-client
    - MariaDB
    - Redis
    - Swagger
- Infra
    - AWS EC2
    - NGINX
    - Jenkins
    - Docker
- Tools
    - Jira
    - Postman
    - Notion
    - Figma
    - GitLab
    - Discord

<br>

### 🍡 화면 설계

![image](https://github.com/sun-mart-egg/funding-gift/assets/66028419/5de6a730-6ed0-449d-a0d7-abf5dbb677ae)

<br>

### 📐 ERD 설계

![image](https://github.com/sun-mart-egg/funding-gift/assets/66028419/10bffdd4-a5f5-4904-afe8-0d0403d408de)

<br>


### 🔎 서비스 아키텍처

![image](https://github.com/sun-mart-egg/funding-gift/assets/66028419/9100b312-f7d5-42a0-8851-09681fd32b11)

<br>

### 👀 팀원 소개

| 임수빈 | 김대영 | 박창준 | 신시은 | 박종혁 | 이민수 |
| - | - | - | - | - | - |
| BE | BE/Infra | BE | FE | FE | FE |
| 리더, 상품/fcm | 펀딩/결제 | oauth/친구 | 리더, 펀딩 | 회원/친구 | 상품 |

<br>

### 🧷 링크

- [Notion](https://fearless-texture-68a.notion.site/PJT-D201-ef79c36622f147288d0bd98d3cc854b7?pvs=4)
- [UCC](https://www.youtube.com/watch?v=mtanQTrCkdE)

<br>
