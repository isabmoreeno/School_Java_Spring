package com.example.school.services; // Ajuste o pacote

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.school.dtos.CourseRequest;
import com.example.school.dtos.CourseResponse;
import com.example.school.entities.Course;
import com.example.school.entities.Student;
import com.example.school.entities.Teacher;
import com.example.school.mappers.CourseMapper;
import com.example.school.repositories.CourseRepository;
import com.example.school.repositories.StudentRepository;
import com.example.school.repositories.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<CourseResponse> getCourses() {
        return repository.findAll()
                .stream()
                .map(CourseMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(long id) {
        return repository.findById(id)
                .map(CourseMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    @Transactional
    public void deleteCourseById(long id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new EntityNotFoundException("Course not found");
    }

    @Transactional
    public CourseResponse saveCourse(CourseRequest request) {
        Course course = CourseMapper.toEntity(request);
        
        // Associa Teachers
        if (request.teacherIds() != null && !request.teacherIds().isEmpty()) {
            Set<Teacher> teachers = teacherRepository.findAllById(request.teacherIds())
                .stream().collect(Collectors.toSet());
            
            if (teachers.size() != request.teacherIds().size()) {
                throw new EntityNotFoundException("One or more Teachers not found");
            }
            course.setTeachers(teachers);
        }
        
        // Associa Students
        if (request.studentIds() != null && !request.studentIds().isEmpty()) {
            Set<Student> students = studentRepository.findAllById(request.studentIds())
                .stream().collect(Collectors.toSet());
            
            if (students.size() != request.studentIds().size()) {
                throw new EntityNotFoundException("One or more Students not found");
            }
            course.setStudents(students);
        }
        
        Course savedCourse = repository.save(course);
        return CourseMapper.toDTO(savedCourse);
    }

    @Transactional
    public void updateCourse(CourseRequest request, long id) {
        Course course = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        
        course.setName(request.name());
        
        // Atualiza Teachers
        course.getTeachers().clear();
        if (request.teacherIds() != null && !request.teacherIds().isEmpty()) {
            Set<Teacher> teachers = teacherRepository.findAllById(request.teacherIds())
                .stream().collect(Collectors.toSet());
            
            if (teachers.size() != request.teacherIds().size()) {
                throw new EntityNotFoundException("One or more Teachers not found");
            }
            course.setTeachers(teachers);
        }
        
        // Atualiza Students
        course.getStudents().clear();
        if (request.studentIds() != null && !request.studentIds().isEmpty()) {
            Set<Student> students = studentRepository.findAllById(request.studentIds())
                .stream().collect(Collectors.toSet());
            
            if (students.size() != request.studentIds().size()) {
                throw new EntityNotFoundException("One or more Students not found");
            }
            course.setStudents(students);
        }
        
        repository.save(course);
    }
}