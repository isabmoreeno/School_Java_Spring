package com.example.school.dtos;

import java.util.Set;

public record CourseRequest(
    String name,
    Long teacherId,
    Set<Long> studentIds
) {}


