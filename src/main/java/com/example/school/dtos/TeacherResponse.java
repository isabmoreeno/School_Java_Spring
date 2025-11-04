package com.example.school.dtos;

// Usando Record (Java 16+), que gera o construtor automaticamente.
public record TeacherResponse(
    Long id,
    String name, // Campo 2: String
    String email         // Campo 3: String
) {}