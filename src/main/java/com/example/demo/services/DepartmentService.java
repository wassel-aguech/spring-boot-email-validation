package com.example.demo.services;


import com.example.demo.Model.Department;
import com.example.demo.repository.DepartmentRepo;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepo repository;

    // 🔹 Lister tous les départements
    public List<Department> getAll() {
        return repository.findAll();
    }
    // 🔹 Ajouter un département
    public Department addDepartment(String name) {

        Department department = new Department();
        department.setName(name);

        return repository.save(department);
    }
}
