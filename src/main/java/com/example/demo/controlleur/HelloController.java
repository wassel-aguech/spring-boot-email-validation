package com.example.demo.controlleur;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    List<String> students = List.of("John Doe", "Jane Doe");
    @GetMapping("")
    public String hello() {
        return "Hello World";
    }


    @GetMapping("/studnte")
    public String studnte() {
        return students.get(0);
    }

    @PostMapping("/studnet")
    public String student(@RequestBody String student) {
        students.add(student);
        return student;

    }

}
