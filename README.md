
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

maven 中央仓库依赖

```$xslt
<dependency>
  <groupId>com.github.bamboo-cn</groupId>
  <artifactId>jt-common-core</artifactId>
  <version>1.0.1</version>
</dependency>
```

以下依赖方式已经作废，推荐MAVEN中央仓库的方式引入
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


## spring-boot web项目启动类

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



### 拓展功能配置
```
spring:
    cache: #缓存配置
        prefix: bamboo #key前缀
        ttlTime: 2000  #缓存时长单位秒
    redis: #redis配置同spring-redis配置相同
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


### 默认支持spring cache使用redis做缓存
- 使用方式和spring cache注解相同
- 使用fastjson作为序列化

具体使用参考以下地址
https://blog.csdn.net/zjcjava/article/details/103920388



## 版更新记录

- 2020-1-10 支持spring cache使用redis做缓存,Redis故障或不可用时仍然执行sql层方法使服务可用
- 2020-1-1  支持跨域,redis分布式锁



