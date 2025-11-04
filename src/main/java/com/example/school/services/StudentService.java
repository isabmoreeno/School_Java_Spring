package com.example.school.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public List<StudentResponse> getStudents() {
        return repository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentById(long id) {
        return repository.findById(id)
                .map(StudentMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    @Transactional
    public void deleteStudentById(long id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new EntityNotFoundException("Student not found");
    }

    @Transactional
    public StudentResponse saveStudent(StudentRequest request) {
        // Segue o padrão de mapeamento para salvar
        Student student = StudentMapper.toEntity(request);
        Student savedStudent = repository.save(student);
        return StudentMapper.toDTO(savedStudent);
    }

    @Transactional
    public void updateStudent(StudentRequest request, long id) {
        Student student = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        
        // --- Estrutura 'verbosa' do update, espelhando o CourseService ---
        student.setName(request.name());
        student.setEmail(request.email());
        // -----------------------------------------------------------------

        repository.save(student);
    }
}