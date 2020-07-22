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
