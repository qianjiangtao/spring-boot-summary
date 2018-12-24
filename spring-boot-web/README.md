##一.静态资源访问

​	Spring Boot 默认为我们提供了静态资源处理，使用 WebMvcAutoConfiguration 中的配置各种属性 ,默认访问静态资源的顺序为:

- classpath:/META-INF/resources

- classpath:/resources

- classpath:/static

- classpath:/public

  

**疑问**：从哪看出来的？



首先既然spring boot说默认是按以上顺序去找资源，那就是说可以自定义配置顺序，那么application.yml中就能配置其访问顺序，来看一段官网说明:

> By default, resources are mapped on `/**`, but you can tune that with the `spring.mvc.static-path-pattern` property. For instance, relocating all resources to`/resources/**` can be achieved as follows: spring.mvc.static-path-pattern=/resources/**



可以看出来是使用**spring.mvc.static-path-pattern****来指定顺序的，那我们在spring自动配置文件中找下这个配置(META-INF/spring-configuration-metadata.json)如下:

```json
{
  "name": "spring.resources.static-locations",
  "type": "java.lang.String[]",
  "description": "Locations of static resources. Defaults to classpath:[\/META-INF\/resources\/, \/resources\/, \/static\/, \/public\/].",
  "sourceType": "org.springframework.boot.autoconfigure.web.ResourceProperties",
  "defaultValue": [
    "classpath:\/META-INF\/resources\/",
    "classpath:\/resources\/",
    "classpath:\/static\/",
    "classpath:\/public\/"
  ]
}
```
 从上面的json配置中很容易看出来访问顺序。


**测试:**

新建以上目录，在每个目录下都放一个hello.html文件，如下:

![1545642098911](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/1545642098911.png)

使用浏览器访问:http://localhost:8080/hello.html **结果: META-INF resource hello** 



##二.模版引擎的使用

Spring Boot提供了默认配置的模板引擎主要有以下几种（本文是基于spring boot 2.1.1.RELEASE）： 

- [FreeMarker](https://freemarker.apache.org/docs/)

- [Groovy](http://docs.groovy-lang.org/docs/next/html/documentation/template-engines.html#_the_markuptemplateengine)

- [Thymeleaf](http://www.thymeleaf.org/)

- [Mustache](https://mustache.github.io/)

  

 **疑问:** 从哪看出spring boot支持哪些模版引擎 ？

 	既然是模版引擎，肯定有对应的视图解析器,不妨我们就搜索下freemarker视图解析器：`FreeMarkerViewResolver`，发现他是继承`AbstractTemplateViewResolver`，既然freemarker继承了这个模版视图解析器，那肯定有其他类继承这个类不妨再看看其子类:

![1545643446323](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/1545643446323.png)

果然看到了Groovy,Mustache，不对为啥没有Thymeleaf？只看看父类`UrlBasedViewResolver`还是没有，再看看父类`AbstractCachingViewResolver`,终于找到了，也验证了我们的猜想

![1545644661364](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/1545644661364.png)



**接下来将已Thymeleaf和freemarker模版引擎为例，做一个简单的入门**



###Thymeleaf的使用

1.套路一：引入spring-boot-starter-xxx.jar

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

2.添加Controller

```java
@Controller
@RequestMapping("/thymeleaf")
public class MyThymeleafController {

    @GetMapping(value = "/index")
    public String thymeleafIndex(ModelMap modelMap) {
        modelMap.addAttribute("username", "Tony-J");
        return "thymeleaf";
    }
}
```

3.添加thymeleaf页面（thymeleaf.html）

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>learn Resources</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1 th:text="${username}"/>
</body>
</html>
```

4.测试（http://localhost:8080/thymeleaf/index）

结果:**Tony-J**



### Freemarker的使用

1.套路一:引入spring-boot-starter-xxx.jar

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

2.添加Controller

```java
@Controller
@RequestMapping(value = "/freemarker")
public class MyFreemarkerController {
    @GetMapping(value = "/index")
    public String freemarkerIndex(ModelMap modelMap) {
        modelMap.addAttribute("username", "Tony-J");
        return "freemarker";
    }
}
```

3.添加freemarker页面

```html
<!DOCTYPE html>
<html>
<head lang="en">
    <title>Spring Boot Demo - FreeMarker</title>
    <link href="/css/index.css" rel="stylesheet">
</head>
<body>
<h2>${username}<h2>
</body>
</html>
```

4.测试(http://localhost:8080/freemarker/index)

结果:**Tony-J**



## 三,Swagger的使用

至于swagger是什么这里就不说明了,直接上代码

1.添加swagger jar包

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.2.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.2.2</version>
</dependency>
```

2.创建Swagger2配置类

```java
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.lvmama.tony.controller"))
            .paths(PathSelectors.any())
            .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Boot Swagger Build Restful Api")
            .description("user info")
            .version("1.0")
            .build();
    }
}
```

3.创建测试类

```java
@RestController
@RequestMapping(value = "/swagger")
public class MySwaggerRestController {

    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>());

    @ApiOperation(value = "获取用户列表")
    @GetMapping
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping
    public String postUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Integer id) {
        return users.get(id);
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer"),
        @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @PutMapping(value = "/{id}")
    public String putUser(@PathVariable Integer id, @RequestBody User user) {
        User u = users.get(id);
        u.setUsername(user.getUsername());
        users.put(id, u);
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}")
    public String deleteUser(@PathVariable Integer id) {
        users.remove(id);
        return "success";
    }
}
```



4.测试（http://localhost:8080/swagger-ui.html）

![1545652271167](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/1545652271167.png)

**创建用户：**

![1545652538297](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/1545652538297.png)



查询用户列表:

![1545652614574](https://raw.githubusercontent.com/qianjiangtao/spring-boot-summary/master/spring-boot-image/web/1545652614574.png)