package com.example.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.school.dtos.StudentRequest;
import com.example.school.dtos.StudentResponse;
import com.example.school.entities.Student;
import com.example.school.mappers.StudentMapper;
import com.example.school.repositories.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public List<StudentResponse> getStudents() {
        return repository.findAll()
                         .stream()
                         .map(StudentMapper::toDTO)
                         .toList();
    }

    public StudentResponse getStudentById(long id) {
        return repository.findById(id)
                         .map(StudentMapper::toDTO)
                         .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    public StudentResponse saveStudent(StudentRequest request) {
        Student student = StudentMapper.toEntity(request);
        Student saved = repository.save(student);
        return StudentMapper.toDTO(saved);
    }

    public void updateStudent(long id, StudentRequest request) {
        Student student = repository.findById(id)
                                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        student.setName(request.name());
        repository.save(student);
    }

    public void deleteStudent(long id) {
        if (repository.existsById(id)) repository.deleteById(id);
        else throw new EntityNotFoundException("Student not found");
    }
}
