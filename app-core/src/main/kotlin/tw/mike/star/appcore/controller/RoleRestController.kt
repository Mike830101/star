package tw.mike.star.appcore.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping()
class RoleRestController {

    @GetMapping("a")
    fun getTest(){
        println("getTest")
    }

    @PostMapping("a")
    fun postTest(){
        println("postTest")
    }
}