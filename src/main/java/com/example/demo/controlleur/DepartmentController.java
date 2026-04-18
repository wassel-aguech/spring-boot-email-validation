package com.example.demo.controlleur;

import com.example.demo.repository.DepartmentRepo;
import com.example.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;
    @PostMapping("/departments/add")
    public String addDepartment(@RequestBody String name) {
        service.addDepartment(name);
        return "redirect:/departments";
    }
}
