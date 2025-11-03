package com.example.school.mappers;

// O import de java.util.stream.Collectors não é mais necessário para este método
// import java.util.stream.Collectors; 

import com.example.school.dtos.StudentRequest;
import com.example.school.dtos.StudentResponse;
import com.example.school.entities.Student;
// O import de CourseMapper não é mais necessário aqui
// import com.example.school.mappers.CourseMapper; 

public class StudentMapper {

    public static Student toEntity(StudentRequest request) {
        Student s = new Student();
        s.setName(request.name());
        return s;
    }

    public static StudentResponse toDTO(Student student) {
        // CORRIGIDO: Retorna o DTO simplificado sem a coleção de cursos
        return new StudentResponse(
            student.getId(),
            student.getName()
            /* Removido: student.getCourses().stream()
                        .map(CourseMapper::toResponse)
                        .collect(Collectors.toSet())
            */
        );
    }
}
