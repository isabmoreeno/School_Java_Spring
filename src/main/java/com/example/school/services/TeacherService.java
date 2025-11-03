package com.example.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.school.dtos.TeacherRequest;
import com.example.school.dtos.TeacherResponse;
import com.example.school.entities.Teacher;
import com.example.school.mappers.TeacherMapper;
import com.example.school.repositories.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository repository;

    public List<TeacherResponse> getTeachers() {
        return repository.findAll()
                         .stream()
                         .map(TeacherMapper::toDTO)
                         .toList();
    }

    public TeacherResponse getTeacherById(long id) {
        return repository.findById(id)
                         .map(TeacherMapper::toDTO)
                         .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    public TeacherResponse saveTeacher(TeacherRequest request) {
        Teacher teacher = TeacherMapper.toEntity(request);
        Teacher saved = repository.save(teacher);
        return TeacherMapper.toDTO(saved);
    }

    public void updateTeacher(long id, TeacherRequest request) {
        Teacher teacher = repository.findById(id)
                                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        teacher.setName(request.name());
        repository.save(teacher);
    }

    public void deleteTeacher(long id) {
        if (repository.existsById(id)) repository.deleteById(id);
        else throw new EntityNotFoundException("Teacher not found");
    }
}
