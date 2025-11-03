package com.example.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.school.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}