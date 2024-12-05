package com.SistemPos.sistemapos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
