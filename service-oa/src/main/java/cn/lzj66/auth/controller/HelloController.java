package cn.lzj66.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: HelloController
 * Package: cn.lzj66.auth.controller
 * Description:
 *
 * @Author 工学院-liuzhaojun
 * @Create 2024/6/3 16:03
 */
@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/world")
    public String hello() {
        return "hello World";
    }
}
