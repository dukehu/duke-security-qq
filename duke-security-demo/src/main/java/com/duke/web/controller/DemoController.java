package com.duke.web.controller;

import com.duke.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by duke on 2017/12/23
 */
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }

    @GetMapping("/hello/{id}")
    public String selectOne(@PathVariable String id) {
        return demoService.selectOne(id).getName();
    }

}
