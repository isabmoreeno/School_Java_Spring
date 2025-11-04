package com.example.school.dtos; // Ajuste o pacote conforme seu projeto

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CourseRequest(
    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
    String name,

    // IDs dos professores a serem associados
    Set<Long> teacherIds,

    // IDs dos estudantes a serem associados
    Set<Long> studentIds
) {}