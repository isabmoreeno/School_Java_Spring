package com.example.school.dtos;

public record StudentResponse(
    Long id,
    String name,
    String email
) {}