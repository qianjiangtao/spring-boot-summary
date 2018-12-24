### 快速启动一个spring boot web项目



1.使用idea `Spring Initializr`快速创建一个spring boot项目



2.引入`spring-boot-starter-web` 依赖jar包

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

3.创建一个Controller

```java
@RestController
public class MyRestController {
    @GetMapping(value = "/queryUser/{id}")
    public Object queryUser(@PathVariable Integer id,String username) {
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        return user;
    }
}
```

4.启动main方法

```java
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

5.控制台启动日志

```java
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.1.RELEASE)

......
    
2018-12-24 14:22:54.546  INFO 6796 --- [  restartedMain] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2018-12-24 14:22:55.001  INFO 6796 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2018-12-24 14:22:55.107  INFO 6796 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2018-12-24 14:22:55.117  INFO 6796 --- [  restartedMain] com.lvmama.tony.App                      : Started App in 6.907 seconds (JVM running for 7.698)
2018-12-24 14:22:59.692  INFO 6796 --- [nio-8080-exec-3] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2018-12-24 14:22:59.692  INFO 6796 --- [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2018-12-24 14:22:59.707  INFO 6796 --- [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : Completed initialization in 15 ms
```

6.访问http://localhost:8080/queryUser/2?username=tony-J

```json
{"id":2,"username":"tony-J"}
```



### 思考

​	spring boot rest默认支持json格式响应，那么如何拓展呢？我们先要知道Spring Boot中处理HTTP请求的实现是采用的Spring MVC。而在Spring MVC中有一个消息转换器这个概念，它主要负责处理各种不同格式的请求数据进行处理，并包转换成对象 在Spring MVC中定义了`HttpMessageConverter`接口


> HTTP请求的Content-Type有各种不同格式定义，如果要支持Xml格式的消息转换，就必须要使用对应的转换器。
Spring MVC中默认已经有一套采用Jackson实现的转换器MappingJackson2XmlHttpMessageConverter



在`WebMvcConfigurationSupport`中有个这么几段代码:

```java
//判断是否存在com.fasterxml.jackson.dataformat.xml.XmlMapper，如果存在则添加xml转化器
static {
    ......
        jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);
    .......
}

......
if (jackson2XmlPresent) {
    Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
    if (this.applicationContext != null) {
        builder.applicationContext(this.applicationContext);
    }
    messageConverters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));
}
.....
```



那么想拓展xml请求响应则很简单了，添加`jackson`依赖包就可以了，如下：

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```



再次重启访问：`http://localhost:8080/queryUser/2?username=tony-J`

```xml
<User>
    <id>2</id>
    <username>tony-J</username>
</User>
```







