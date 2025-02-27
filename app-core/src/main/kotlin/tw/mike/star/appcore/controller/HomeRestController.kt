package tw.mike.star.appcore.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class HomeRestController {

    @GetMapping("/error")
    fun error():String {
        return "error~!!!!"
    }

    @GetMapping("/login")
    fun login():String {
        return "login.html"
    }

    @GetMapping("/home")
    fun home():String {
        return "home.html"
    }
}