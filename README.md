# Dubbo、Zookeeper、Spring Boot入门整合



## 安装Zookeeper

**下载**：[ https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.6.1/apache-zookeeper-3.6.1-bin.tar.gz ]( https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.6.1/apache-zookeeper-3.6.1-bin.tar.gz )



**在conf文件夹下，将zoo_sample.cfg 修改成zoo.cfg**



**进入bin文件夹下进行启动。**

`zkServer.cmd`



## 安装Dubbo

下载源代码：[ https://github.com/apache/dubbo-admin/tree/master ]( https://github.com/apache/dubbo-admin/tree/master )



**打包成jar包**

```bash
mvn clean package -Dmaven.test.skip=true
```



进入dubbo-admin/target，启动jar包

`java -jar xxxx.jar`

> 进入	dubbo图形化监控平台的地址：
> `http://localhost:7001`


## 项目整合测试

### 1. 创建一个空的Maven项目



**依赖：**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>springboot-dubbo-zookeeper</artifactId>
    <version>1.0-SNAPSHOT</version>

    <modules>
        <module>consumer-server</module>
        <module>provider-server</module>
    </modules>


    <dependencies>

        <!--Dubbo依赖-->
        <!-- https://mvnrepository.com/artifact/org.apache.dubbo/dubbo-spring-boot-starter -->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo-spring-boot-starter</artifactId>
            <version>2.7.7</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>


        <!--zookeeper-->
        <!-- https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper -->
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.6.1</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>com.github.sgroschupf</groupId>
            <artifactId>zkclient</artifactId>
            <version>0.1</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-recipes -->
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-recipes</artifactId>
            <version>5.1.0</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.apache.curator/curator-framework -->
        <dependency>
            <groupId>org.apache.curator</groupId>
            <artifactId>curator-framework</artifactId>
            <version>5.1.0</version>
        </dependency>


    </dependencies>


</project>
```





### 2. 创建springboot项目provider-server模块

```properties

server.port=8001

# 服务应用的名字
dubbo.application.name=provider-server

# 注册中心的地址
dubbo.registry.address=zookeeper://127.0.0.1:2181

# 哪些服务要注册
dubbo.scan.base-packages=cn.lacknb.service
```





```java
package cn.lacknb.service;


import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * zookeeper：服务注册与发现
 * @DubboService 可以被扫描到，在项目 一 启动就自动注册到注册中心
 * @Service  放到Spring 容器
 * @author Administrator
 */
@DubboService
@Component
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello() {
        return "Hello Dubbo Zookeeper Spring Boot!!";
    }
}

```





### 3. 创建springboot项目 consume-server模块



```properties
server.port=8002

# 消费者去哪里拿服务 需要暴露自己的名字
dubbo.application.name=consumer-server


# 注册中心的地址
dubbo.registry.address=zookeeper://127.0.0.1:2181


```



```java
package cn.lacknb.service;


import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @DubboReference
    HelloService helloService;

    public void test () {
        System.out.println("收到的 => " + helloService.sayHello());
    }


}

```



```java
package cn.lacknb;

import cn.lacknb.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerServerApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        userService.test();
    }

}

```

