package com.example.school.mappers;

import java.util.stream.Collectors;

import com.example.school.dtos.CourseRequest;
import com.example.school.dtos.CourseResponse;
import com.example.school.entities.Course;
import com.example.school.mappers.StudentMapper;
import com.example.school.mappers.TeacherMapper;

public class CourseMapper {

    public static Course toEntity(CourseRequest request) {
        Course c = new Course();
        c.setName(request.name());
        return c;
    }

    public static CourseResponse toResponse(Course course) {
        return new CourseResponse(
            course.getId(),
            course.getName(),
            course.getTeacher() != null ? TeacherMapper.toDTO(course.getTeacher()) : null,
            course.getStudents().stream()
                  .map(StudentMapper::toDTO)
                  .collect(Collectors.toSet())
        );
    }
}
