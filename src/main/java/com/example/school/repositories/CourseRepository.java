package com.example.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.school.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
