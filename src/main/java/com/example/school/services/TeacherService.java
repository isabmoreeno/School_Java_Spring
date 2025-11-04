package com.example.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public List<TeacherResponse> getTeachers() {
        return repository.findAll()
                .stream()
                .map(TeacherMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeacherResponse getTeacherById(long id) {
        return repository.findById(id)
                .map(TeacherMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
    }

    @Transactional
    public void deleteTeacherById(long id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new EntityNotFoundException("Teacher not found");
    }

    @Transactional
    public TeacherResponse saveTeacher(TeacherRequest request) {
        Teacher teacher = TeacherMapper.toEntity(request);
        Teacher savedTeacher = repository.save(teacher);
        return TeacherMapper.toDTO(savedTeacher);
    }

    @Transactional
    public void updateTeacher(TeacherRequest request, long id) {
        Teacher teacher = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        
        TeacherMapper.updateEntity(teacher, request);
        repository.save(teacher);
    }
}