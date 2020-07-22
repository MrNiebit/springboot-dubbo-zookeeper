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
