package com.dza.catalogservice

import com.dza.catalogservice.config.PolarProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(val polarProperties: PolarProperties) {

    @GetMapping("/")
    fun getGreeting(): String = polarProperties.greeting
}