## item-service
- 핵심 비즈니스 로직
  - 항상 데이터를 `itemRepository.save(new Item( ... ))` 해 줘야 함
  - `itemRepository` 는 데이터 임시 저장소
- MVC
  - Model : View 와 Controller 사이에 데이터 전송 다리. View에서 사용될 데이터들은 모두 Controller에서 전달해 줘야 함.(`Model model` 선언 or `@ModelAttribute` 사용)
  - View : HTML파일(static), Thymeleaf파일(templates)
  - Controller : 한 파일에 `@RequestMapping("url 주소")` 하여 들어가 있음. return 값은 html 파일 경로, but redirect 하면 url 경로로 이동 가능 ~> PRG
- PRG
  - 상품수정(Post) 후에 상품상세 페이지로 다시 돌아감. 이때 새로고침 해 주면 Post가 계속 실행되어 값이 누적됨
  - 뷰 템플릿으로 이동하지 않고, 상품상세 화면으로 리다이렉트 호출해 주면 Get으로 처리되어 새로 고침 문제 해결 가능
- *오타나 / 미기입으로 인해 화면 렌더링 이슈가 자주 발생하니 신경 쓸 것

---

## DB 연동
### MySQL
[MySQL 접근]
```
mysql -u root -p 
```

[database 생성 + 권한부여]
```
CREATE DATABASE connectdb;
CREATE USER guestbook@localhost IDENTIFIED BY 'connect123!@#';
GRANT ALL PRIVILEGES ON connectdb.* TO 'guestbook'@'localhost';
FLUSH PRIVILEGES:
```

[사용 중인 데이터베이스 전환]
```
use mydb;
```

---

### 연동
1. `bulid.gradle` 파일에 MySQL 의존성 추가
```
dependencies {
implementation 'mysql:mysql-connector-java'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
```

2. `application.properties` 에 DB 정보 추가
[설정 샘플]
```
# MySQL 설정
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver 

# DB Source URL
spring.datasource.url=jdbc:mysql://<IP>:<Port/<DB>?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul 

# DB username
spring.datasource.username=<username> 

# DB password
spring.datasource.password=<password> 

# true 설정시 JPA 쿼리문 확인 가능
spring.jpa.show-sql=true 

# DDL(create, alter, drop) 정의시 DB의 고유 기능을 사용할 수 있다.
spring.jpa.hibernate.ddl-auto=update 

# JPA의 구현체인 Hibernate가 동작하면서 발생한 SQL의 가독성을 높여준다.
spring.jpa.properties.hibernate.format_sql=true

```

[설정 예시]
```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/itemservice?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul

spring.datasource.username=root
spring.datasource.password=12345678

spring.jpa.show-sql=true 
spring.jpa.hibernate.ddl-auto=update 
spring.jpa.properties.hibernate.format_sql=true
```

[MySQL Workbench에서 정보 찾기]
![root](https://velog.velcdn.com/images/sians0209/post/be2060fc-043e-4bfc-8524-399dd65e3dde/image.png)
- `<IP>` 에 밑줄친 주소를 입력하면 됨
- `<DB>` 는 사용자가 `create database` 할 때 생성한 Database의 이름
- `<username>`과 `<password>` 는 db 시작할 때 사용자가 입력한 이름과 비밀번호

[Intellij 실행화면]
![DB 연결 실행화면](https://velog.velcdn.com/images/sians0209/post/3b62aa25-003e-47c8-9896-e45765675a0d/image.png)


3.JPA Hibernate 엔티티(Entity) 생성 확인
[Demo Entity 생성]
```
@Table(name = "table_demo")
@Entity
public class Demo {

    @Id @GeneratedValue
    private Long id;

    private String demoText;

}
```

[Intellij에서 쿼리문 확인]
![쿼리문 확인](https://velog.velcdn.com/images/sians0209/post/f5a91fab-05ff-417f-9872-9c9112f9cacb/image.png)

[MySQL에서 table 확인]
![table 확인](https://velog.velcdn.com/images/sians0209/post/3b8f600a-1c1e-4926-b5ff-e5420c7d2c2f/image.png)
