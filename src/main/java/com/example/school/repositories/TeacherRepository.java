package com.example.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.school.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}