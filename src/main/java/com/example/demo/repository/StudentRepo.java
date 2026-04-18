package com.example.demo.repository;

import com.example.demo.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    List<Student> findByDepartmentId(Long departmentId);

    List<Student> findByCourseId(Long courseId);

}



