package com.example.school.dtos;

import java.util.Set;
import com.example.school.dtos.StudentResponse;
import com.example.school.dtos.TeacherResponse;

public record CourseResponse(
    Long id,
    String name,
    TeacherResponse teacher,
    Set<StudentResponse> students
) {}