package com.server.literasea.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }
}