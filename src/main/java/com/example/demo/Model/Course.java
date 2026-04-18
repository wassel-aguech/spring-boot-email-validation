package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Student> students;


}
