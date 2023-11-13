package com.jpabook.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    /* model : 컨트롤러에서 어떤 데이터를 실어서 view에 넘길수있다 */
    @GetMapping("hello") // http://localhost:8080/hello
    public String hello(Model model){
        model.addAttribute("data", "hello!");
        return "hello"; // 연결할 view 화면이름
    }
}
