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