package com.example.school.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.school.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeacherId(Long teacherId);
}
