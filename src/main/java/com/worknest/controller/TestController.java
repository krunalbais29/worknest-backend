package com.worknest.controller;

//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TestController {

    @GetMapping("/api/test")
    public String testSecure() {
        return "JWT is working! You are authenticated.";
    }
}