package com.example.school.mappers;

import com.example.school.dtos.StudentRequest;
import com.example.school.dtos.StudentResponse;
import com.example.school.entities.Student;

public class StudentMapper {

    // Entidade para DTO de Resposta
    public static StudentResponse toDTO(Student entity) {
        return new StudentResponse(
            entity.getId(),
            entity.getName(),
            entity.getEmail()
        );
    }

    // DTO de Requisição para Entidade
    public static Student toEntity(StudentRequest dto) {
        Student entity = new Student();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        return entity;
    }
    
    // DTO de Requisição para Entidade (Update)
    public static void updateEntity(Student entity, StudentRequest dto) {
        entity.setName(dto.name());
        entity.setEmail(dto.email());
    }
}