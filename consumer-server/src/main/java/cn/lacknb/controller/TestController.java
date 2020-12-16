package cn.lacknb.controller;

import cn.lacknb.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gitsilence
 * @version 1.0
 * @date 2020/12/16 15:29
 * @mail gitsilence@lacknb.cn
 * 控制器调用测试
 */
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String dubboTest (){
        return userService.test();
    }

}
