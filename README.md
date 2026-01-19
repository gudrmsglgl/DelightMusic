# DelightMusic

로컬 음악 파일을 재생하는 안드로이드 앱

## 기술 스택

- Kotlin
- Jetpack Compose
- Media3 (ExoPlayer)
- Hilt
- Coroutines & Flow

## 모듈 구조

```
app/                    # 앱 진입점

core/
├── model/              # 데이터 모델 (Music)
├── designsystem/       # 앱 테마, 컬러
│
├── datasource/
│   ├── datasource-musiclist-api/   # 데이터소스 인터페이스
│   └── datasource-musiclist/       # 로컬 MediaStore 구현
│
├── domain/
│   ├── domain-musiclist-api/       # UseCase 인터페이스
│   └── domain-musiclist/           # UseCase 구현
│
├── feature/
│   ├── musiclist/      # 음악 목록 화면
│   └── musicdetail/    # 재생 화면
│
└── player/
    ├── player-api/     # 플레이어 인터페이스
    └── player/         # Media3 기반 구현
```

```
