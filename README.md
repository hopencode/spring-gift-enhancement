# spring-gift-enhancement

## STEP0
미션2에서 진행했던 코드 옮기기

## STEP1 - 엔티티 매핑
1. 엔티티 리팩토링
- 데이터베이스 테이블과 매핑될 엔티티 클래스를 정의
- `@Entity` 어노테이션을 사용하여 모든 도메인 객체를 JPA 엔티티로 변환

2. Repository 계층 JpaRepository를 상속받는 인터페이스로 변경
3. 변경된 인터페이스를 사용하도록 서비스 계층 수정
4. `@DataJpaTest`를 사용하여 JPA Repository가 올바르게 동작하는지 테스트
