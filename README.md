# 프로젝트 컨벤션

## 어노테이션
어노테이션을 통해 각 참여자의 수정 범위를 구분하며 자신이 책임지는 클래스 및 함수 외에는 별도의 보고 없이 수정을 금지합니다.

## 엔터티 오브젝트
Customer, Employee 와 같은 엔터티 오브젝트는 반드시 toString 함수를 오버라이딩하여
SQL의 VALUES문에 사용할 수 있는 형태로 객체의 값들을 반환해야합니다.
예를 들어 Claim 객체의 toString 함수는 아래와 같이 구현되어있습니다.

```java
 @Override
    public String toString() {
        return "'" + claimId + "','" + customerId + "','" + employeeId + "','" + date + "','"
                + type + "','" + description + "','" + location + "','"
                + report + "'," + compensation + ",'" + reviewer + "','"
                + status + "'";
    }
```


# How can i use database bootstrap script?

1. .env 파일을 생성한다.
2. .env 파일을 아래 양식에 맞춰 작성한다.
```
DB_ID=아이디를_입력해주세요
DB_PASSWORD=비밀번호를_입력해주세요
```
3. terminal 환경에서 ./bootstrap-database.sh를 실행한다. (만약 실행 할 수 없는 경우 `chmod +x ./bootstrap-database.sh`를 입력해준다.)
    * docker, local 환경을 지원한다.
    * ./sql/initialization.sql을 기반으로 database를 만드니 수정할 부분이 있다면 해당 sql을 수정해주면 된다.
    * database 이름은 기존 DB 충돌을 방지하기 위해 nemne_insurance라는 db를 사용한다. 