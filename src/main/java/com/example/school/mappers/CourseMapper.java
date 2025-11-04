package com.example.school.mappers; // Ajuste o pacote

import com.example.school.dtos.CourseRequest;
import com.example.school.dtos.CourseResponse;
import com.example.school.dtos.StudentResponse;
import com.example.school.dtos.TeacherResponse;
import com.example.school.entities.Course;

import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {

    // Converte Entidade para DTO de Resposta (JSON final)
    public static CourseResponse toDTO(Course entity) {
        // Mapeia Teachers
        List<TeacherResponse> teacherResponses = entity.getTeachers().stream()
            .map(t -> new TeacherResponse(t.getId(), t.getName(), t.getEmail()))
            .collect(Collectors.toList());

        // Mapeia Students
        List<StudentResponse> studentResponses = entity.getStudents().stream()
            .map(s -> new StudentResponse(s.getId(), s.getName(), s.getEmail()))
            .collect(Collectors.toList());

        return new CourseResponse(
            entity.getId(),
            entity.getName(),
            teacherResponses,
            studentResponses
        );
    }

    // Converte DTO de Requisição para Entidade
    public static Course toEntity(CourseRequest dto) {
        Course entity = new Course();
        entity.setName(dto.name());
        // Relacionamentos (teachers/students) são definidos no Service
        return entity;
    }
}