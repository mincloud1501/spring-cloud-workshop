# spring-cloud-workshop
MSA Development Project with Spring Boot

# Hystrix Dashboard
Hystrix Dashboard는 앞의 Hystrix 설정에 따른 Circuit breaker의 상태를 모니터링 할 수 있는 dashboard를 제공해주는 라이브러리이다. 사실 라이브러리라기 보다는 솔루션에 가깝다고 할 정도로 간단한 설정으로 실행할 수 있다.

## Zuul의 build.gradle에 dependency 추가
```
compile('org.springframework.cloud:spring-cloud-starter-netflix-hystrix-dashboard')
compile('org.springframework.boot:spring-boot-starter-actuator')
```

## Zuul의 application.yml에 management 추가
```
spring:
  application:
    name: zuul

server:
  port: 8765

management:
  security:
    enabled: false
  endpoints:
    web:
      exposure:
        include: hystrix.stream
      base-path: /
```

## ZuulApplication.java에 Annotation 추가
```
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine

public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
```

## Hystrix Dashboard Connction
# Dashboard
![dashboard1](images/dashboard1.png)

# Dashboard
![dashboard2](images/dashboard2.png)

