package com.example.school.mappers;

import com.example.school.dtos.TeacherRequest;
import com.example.school.dtos.TeacherResponse;
import com.example.school.entities.Teacher;

public class TeacherMapper {

    // Entidade para DTO de Resposta
    public static TeacherResponse toDTO(Teacher entity) {
        return new TeacherResponse(
            entity.getId(),
            entity.getName(),
            entity.getEmail()
        );
    }

    // DTO de Requisição para Entidade
    public static Teacher toEntity(TeacherRequest dto) {
        Teacher entity = new Teacher();
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        return entity;
    }
    
    // DTO de Requisição para Entidade (Update)
    public static void updateEntity(Teacher entity, TeacherRequest dto) {
        entity.setName(dto.name());
        entity.setEmail(dto.email());
    }
}