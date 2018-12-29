 ## spring boot 2.1整合Redis



### 1.普通字符串存储

1.引入`spring-boot-starter-data-redis`jar包,注意spring boot 2.1 没有对应的`spring-boot-starter-redis`版本，改名为`spring-boot-starter-data-redis`。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

2.在application中添加redis配置

```yml
spring:
  redis:
    host: 192.168.187.11
    port: 6379
```

3.测试

redis客户端查看,没有任何key

```shell
192.168.187.11:6379> keys *
(empty list or set)
```

```java
@RestController
@RequestMapping(value = "/string")
public class RedisStringController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping(value = "/put")
    public void put(String key, @RequestParam(required = false, defaultValue = "default") String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @GetMapping(value = "/get")
    public Object get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
```

使用postman送请求: `localhost:8080/string/put?key=hello&value=world `

![1546052449553](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546052449553.png)



`localhost:8080/string/get?key=hello `

![1546052551231](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546052551231.png)

### 二. 对象存储

在上述使用中，是无法存储对象的，存储对象的话需要使用RedisTemplate并且要使用响应的序列化机制,下面我们就来测试下:

1.引入序列化的jar包，这里我们是使用jackson

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.9.5</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.5</version>
</dependency>
```

2.添加redis配置类

```java
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //使用StringRedisSerializer来序列化和反序列化redis的ke
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //开启事务
        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }
}
```



3.添加测试controller

```java
@RestController
@RequestMapping(value = "/object")
public class RedisObjectController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/get/{username}")
    public Object get(@PathVariable String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @PutMapping("/put")
    public void put(String username, String nickname) {
        User user = new User(username, nickname);
        redisTemplate.opsForValue().set(username, user);
    }
}
```



4.使用postman测试

![1546053511816](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546053511816.png)

![1546053586241](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546053586241.png)

**使用redis客户端查看**

```shell
192.168.187.11:6379> get qianjiangtao
"{\"@class\":\"com.lvmama.tony.model.User\",\"username\":\"qianjiangtao\",\"nickname\":\"Tony-J\"}"
```



### 三,spring boot整合redis自动化配置原理分析

​	我们都知道spring boot自动化配置中的配置都是通过`spring-configuration-metadata.json`来约束的，同理redis也是这样的，我们配置了`spring.redis.host`,不妨来找下这个配置项

```
{
"name": "spring.redis.host",
"type": "java.lang.String",
"description": "Redis server host.",
"sourceType": "org.springframework.boot.autoconfigure.data.redis.RedisProperties",
"defaultValue": "localhost"
}
```

从这能看出来redis的配置都是通过`RedisProperties`这个类来配置的，在这里面我们能看到众多的redis配置及默认配置，我们可以从一个入口切入，就拿`port`切入,来看下port在哪设置的

![1546054112690](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546054112690.png)



`org.springframework.boot.autoconfigure.data.redis.RedisConnectionConfiguration#getStandaloneConfig`

看出在RedisConnectionConfiguration中的getStandaloneConfig中赋值的,那这个方法又是谁调用的呢?继续找?

![1546054322661](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546054322661.png)

从图中能看出来有两个地方可能会调用，从类的名字能看出来，spring boot是支持Jedis和Lettuce两种客户端来操作redis，那到底是用哪个呢? 都看看呗

![1546054523509](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546054523509.png)



![1546054542585](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546054542585.png)



从图中截取的源码中能看出来，我是使用了`LettuceConnectionConfiguration`，看注解是我引入了`RedisClient`，我什么时候引入的？于是我就看看maven的依赖

![1546054690666](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546054690666.png)



**从maven依赖中能看出一些重要的信息:**

1.spring-boot-starter-data-redis中其实用的是spring-data-redis，其实是包装了下

2.依赖了lettuce-core，原来是从这里引入的，怪不得



**如何验证呢？不能瞎说**

​	要想知道很简单的，在我们自己写的RedisConfig中打下断点，看看用的`RedisConnectionFactory`到底是不是`LettuceConnectionFactory`就能证明了

![1546055230647](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/redis/1546055230647.png)



果然如此！



简单的流程就是：

1.spring boot通过application配置加载redis配置

2.解析封装成RedisProperties

3.根据`@ConditionalOnClass`判断使用哪个Redis客户端，封装成`LettuceClientConfiguration`并创建`LettuceConnectionFactory`

4.通过@Bean创建我们自己的配置类在`LettuceConnectionFactory`基础上添加我们自己自定义的配置