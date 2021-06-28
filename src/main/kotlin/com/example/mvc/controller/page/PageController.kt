package com.example.mvc.controller.page

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {

    @GetMapping("/main")
    fun main(): String {
        println("init mian")
        return "main.html"
    }
}