package com.example.demo.services;

import com.example.demo.Model.Department;
import com.example.demo.Model.Student;
import com.example.demo.repository.DepartmentRepo;
import com.example.demo.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepository;
    private final DepartmentRepo departmentRepository;

    public void addStudent(String name, int age, Long departmentId) {

        Department department = departmentRepository.findById(departmentId).orElseThrow();
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setRegistrationDate(LocalDate.now());
        student.setDepartment(department);

        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByDepartment(Long departmentId) {
        return studentRepository.findByDepartmentId(departmentId);
    }

    public List<Student> getStudentsByCourse(Long courseId) {
        return studentRepository.findByCourseId(courseId);
    }
}

