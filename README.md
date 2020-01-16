
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


### spring-boot web项目启动类

```
@SpringBootApplication
@RestController
@SpringBootApplication(scanBasePackages = {"com.bamboo.common"})//加入扫描路径
@Import(value = {CorsConfig.class, LogFilter.class,SynRedisLockAspect.class}) //跨域,接口访问请求日志,redis分布式锁
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public Object index(){
		return "helll demo"+DateUtils.getDate();
	}


    /**分布式锁**/
    @RequestMapping("/synLock/{uid}")
	@SynRedisLock(path = "syn:test:%s", indexProps = {"0"})
	public Object synLock(@PathVariable("uid")Integer uid){
		redisClient.set("test",uid.toString());
		try {
			Thread.sleep(5000);//停顿5秒
		}catch (Exception e){

		}

		return redisClient.get("test");
	}
}
```
根据需要引入工具类
import 实现可跨域和日志请求拦截处理,分布式锁


### Reids分布式锁
```
    /**分布式锁**/
    @RequestMapping("/synLock/{uid}")
	@SynRedisLock(path = "syn:test:%s", indexProps = {"0"})
    public Object synLock(@PathVariable("uid")Integer uid)
```
path:key路径
indexProps:key路径中替换值对应的参数下标（从0开始）,如果是对象则为:参数名.属性
```
	@SynRedisLock(path = "syn:test:%s", indexProps = {"user.id"})
    public Object synLock(User user)
```

### 缓存功能配置
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

cache配置默认值
- prefix: spring-cache
- ttlTime: 3600 一个小时

### 默认支持spring cache使用redis做缓存
- 使用方式和spring cache注解相同
- 使用fastjson作为序列化
- Cacheable支持自定义单个key设置超时时间


自定义单个key设置超时时间,key加上字符串TTL=1000实现扩展单个KEY过期时间
```$xslt
    //@Cacheable(key = "T(String).valueOf(#code).concat('TTL=10')")
    @Cacheable(key = "#code+'TTL=10'")
    public Double getRetailByCode(Integer code) {
        RetailRatio retailRatio =  retailRatioMapper.selectByPrimaryKey(code);
        return retailRatio.getUnratio();
    }
```
以上两种方式都可以(1.0.3版本已经修复)

具体使用参考以下地址
https://blog.csdn.net/zjcjava/article/details/103920388



## 版更新记录

- 1.0.3[2020-1-15] 修复spring cache单独设置key过期时间bug 
- 1.0.2[2020-1-10] 支持spring cache使用redis做缓存,Redis故障或不可用时仍然执行sql层方法使服务可用
- 1.0.1[2020-1-1]  支持跨域,redis分布式锁


## 个人订阅号

![在这里插入图片描述](https://github.com/BambooZhang/springboot-study/raw/master/20170928183434735.jpg)
