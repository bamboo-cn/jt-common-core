
## jt-common-core
在个人原有组件xm-common-core的基础上组成，主要封装一些常用方法,方便Java项目快速开发

微信订阅号
mu-zhuzi


## 常用通用方法集合

```
├─base
├─beanvalidator
├─cache
├─config
├─constant
├─entity
├─exception
├─http
│  ├─request
│  └─response
└─utils
    └─email
```

maven 依赖

```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
	    <groupId>com.github.bamboo-cn</groupId>
	    <artifactId>jt-common-core</artifactId>
	    <version>1.0.4</version>
	</dependency>
```


例如，spring-boot web项目启动类

```

@SpringBootApplication
@RestController
@Import(value = {CorsConfig.class, LogFilter.class}) //跨域,接口访问请求日志
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public Object index(){
		return "helll demo"+DateUtils.getDate();
	}
}
```

import 实现可跨域和日志请求拦截处理



redis配置
```

    redis:
        host: localhost
        port: 6379
        timeout: 2000
        password: redis
        database: 0
        timeout: 2000
        maxIdle: 10
        maxTotal: 100
        

```
个别属性如果不配置默认和上面一样,password默认是空值