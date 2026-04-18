package com.example.demo.controlleur;

import com.example.demo.Model.Student;
import com.example.demo.services.DepartmentService;
import com.example.demo.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

        private final StudentService studentService;
        private final DepartmentService departmentService;

        @GetMapping("/students")
        public String listStudents(Model model) {
            model.addAttribute("students", studentService.getAllStudents());
            return "students";
        }

        @GetMapping("/students/add")
        public String showForm(Model model) {
            model.addAttribute("departments", departmentService.getAll());
            return "add-student";
        }
        @PostMapping("/students/add")
        public String addStudent(@RequestParam String name,
                                 @RequestParam int age,
                                 @RequestParam Long departmentId) {

            studentService.addStudent(name, age, departmentId);
            return "redirect:/students";
        }
    }


