<br>
<br>
<p align="center">
<img src="https://user-images.githubusercontent.com/84304802/187101391-46357a6f-cd89-47ac-9f27-75f76c7b7dd9.png" alt="git-flow" width=450px height=450px>
</p>
<br>

# 🗺️ B-Map: 장애인 전용 주차구역 안내 서비스
### 2022 서울시 IoT 도시 공공데이터 해커톤 - 🏆 우수상 수상작

<br>

### "💁 비어있으면 주차해도 되는 것 아니에요?”

최근 장애인 주차구역의 필요성을 무시하거나 소홀히 하는 점이 사회적 이슈로 떠오르고 있습니다.
장애인 전용주차구역은, 장애인이 보다 편리하게 이동할 수 있도록 법적으로 지정해 둔 구역입니다.

보건복지부 자료에 따르면, 장애인은 외출 시 자가용을 가장 많이 이용한다고 답했습니다. 또한 지체 장애인의 70%가 운전으로 이동한다고 답했습니다.
이렇듯 많은 장애인분들이 자가용을 주요 이동수단으로 이용하지만 전용 주차 구역은 불법 주차 때문에 효율적으로 이용되지 못하고 있습니다.
실제로 4년 동안 불법 주차 위반 건수는 4배 증가했고, 중복 위반은 7배 증가한 것을 확인할 수 있었습니다.
이러한 문제를 해결하기 위해 서울시는 이미 IoT장비를이용한 서비스를 운영하고 있는데요. 문제점은 이 시스템은 단속만을 위한 시스템이라는 것입니다. 불법 차량이 진입했을 때 빨간등을 표시하여 단속하는 것인데, 정작 사용자는 직접 전용 주차구역에 가지 않으면 빈자리 여부를 알 수 없고, 결국 일반 구역을 이용해야 합니다.
장애인 분들에겐 신고가 아닌 정말로 자신들의 이동권을 보장받을 수 있도록 돕는 서비스가 필요했습니다.

그래서 IoT를 활용한 정보와 장애인의 신체를 고려한 UXUI 로 만들어진 장애인 주차구역 안내 서비스가 바로 **B-Map** 입니다.

<br>


## 📋 컨벤션

### # 브랜치 관리 전략

**⚙️ git-flow**

<p align="center">
  <img src="https://user-images.githubusercontent.com/84304802/148559145-64a8029e-d220-4b80-b02f-eb45a0e07c05.png" alt="git-flow">
</p>



<br>


| 브랜치 종류  | 설명                                                         |
| ------------ | ------------------------------------------------------------ |
| Master(main) | 테스트 서버에서 테스트가 끝나고 운영서버로 배포 할 수 있는 브랜치 |
| develop      | 개발을 위한 브랜치                                           |
| feature      | 하나의 기능을 개발하기 위한 브랜치                           |
| hotfix       | 운영중인 버전에서 발생한 버그를 수정 하는 브랜치             |

- `feature` 브랜치는 하나의 기능을 개발하기 위한 브랜치입니다. 부모는 `develop`이며, 개발이 완료되면 `develop`에 merge합니다. 브랜치 이름은 보통 `feature-1/*`이 됩니다.
- `develop` 브랜치는 개발을 위한 브랜치입니다. 여러 `feature`들이 merge되는 장소이며, 아직 release되지 않은 기능들이 모여 있게 됩니다.
- `master` 브랜치는 실제 운영 중인 서비스의 브랜치입니다. 
- `hotfix` 브랜치는 서비스에 문제가 발생했을 때 핫픽스에 해당하는 브랜치입니다. 기능 개발(`feature`) 등과 달리 빠르게 대처해야 할 필요가 있기 때문에, `master` 브랜치에 직접 merge하는 전략을 취합니다.  `develop`과의 차이가 발생하기 때문에, 나중에 차이를 merge할 필요가 있습니다.

<br>

<br>

### # 브랜치 네이밍

**⚙️ 네이밍 패턴**

```
브랜치 종류-이슈번호/간단한 설명	
```

**Ex)** 이슈번호가 1인 '로그인 기능' 이슈를 구현하는 브랜치를 생성하는 경우, 브랜치 이름을<br> 	`feature-1/login` 로 작성한다.

<br>
<br>

### # 커밋 메시지

**⚙️ 메시지 구조**

```
제목 #이슈번호

본문
```

**Ex)** 이슈번호가 1인 이슈의 기능을 구현한 뒤 커밋을 하는 상황이라면 커밋 메시지의 제목을<br>	`A기능 구현 #1` 으로 작성한다.

<br>

**⚙️ Type**

| 타입 종류 | 설명                                 |
| --------- | ------------------------------------ |
| feat      | 새로운 기능에 대한 커밋              |
| fix       | 수정에 대한 커밋                     |
| bug       | 버그에 대한 커밋                     |
| build     | 빌드 관련 파일 수정에 대한 커밋      |
| ci/cd     | 배포 커밋                            |
| docs      | 문서 수정에 대한 커밋                |
| style     | 코드 스타일 혹은 포맷 등에 관한 커밋 |
| refactor  | 코드 리팩토링에 대한 커밋            |
| test      | 테스트 코드 수정에 대한 커밋         |

<br>

<br>

## 🗺️서비스 플로우

<p align="center">
  <img src="https://user-images.githubusercontent.com/84304802/187105970-f8147dd1-396a-47a3-8a48-aa8775041881.png" alt="아키텍처">
</p>


<br>

<br>

## ⚒️ 기술 스택

<br>

⚙️ **Language**

Java 11

⚙️ **Framework**

Spring boot / Spring Data Jpa / Junit5

⚙️ **DevOps*

AWS: EC2, RDS, SecretManager / Github

⚙️ **DB**

H2DB, MySQL

<br>

<br>

## 👥 파트 및 개발 계획

### **[ 팀원 & 파트 ]**

#### 🖥️ 안드로이드 

- 최승호 [Github](https://github.com/tmdgh1592)
- 윤준서 [Github](https://github.com/lowapple)

#### 🗄️ 백엔드 

- 최승준 [Github](https://github.com/PgmJun)

#### 🎨 디자인

- 윤수정 [Email] abcabcbabc@gmail.com

<br>

**[ 개발 기간 ]** 2022/08/23 ~ 2022/08/27

<br>

📑**Notion**: https://www.notion.so/IoT-97c3d4b04ed94fbe8ff58554dec4f188

📑**참가후기**: https://pgmjun.tistory.com/78

<br>
