package com.example.school.mappers;

// Removido o import de java.util.stream.Collectors pois não é mais usado aqui

import com.example.school.dtos.TeacherRequest;
import com.example.school.dtos.TeacherResponse;
import com.example.school.entities.Teacher;
// O import de CourseMapper não é mais necessário
// import com.example.school.mappers.CourseMapper; 

public class TeacherMapper {

    public static Teacher toEntity(TeacherRequest request) {
        Teacher t = new Teacher();
        t.setName(request.name());
        return t;
    }

    public static TeacherResponse toDTO(Teacher teacher) {
        // CORRIGIDO: Remoção do mapeamento de courses para evitar o StackOverflowError
        return new TeacherResponse(
            teacher.getId(),
            teacher.getName()
            // Removido: teacher.getCourses().stream().map(CourseMapper::toResponse).collect(Collectors.toSet())
        );
    }
}
