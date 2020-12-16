package cn.lacknb.service;


import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @DubboReference
    HelloService helloService;

    public String test () {
        return "收到的 => " + helloService.sayHello();
    }


}
