package com.agustinperez.proyectoSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prueba")
public class ControllerExample {

    @GetMapping("/admin")
    public String helloAdmin(){
        return "Hello admin!!";
    }

}
