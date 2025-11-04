package com.example.school.dtos;

import java.util.List;

public record CourseResponse(
    Long id,
    String name, 
    List<TeacherResponse> teachers,
    List<StudentResponse> students
) {}