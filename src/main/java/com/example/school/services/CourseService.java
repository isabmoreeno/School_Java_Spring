package com.example.school.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.school.dtos.CourseRequest;
import com.example.school.dtos.CourseResponse;
import com.example.school.entities.Course;
import com.example.school.entities.Student;
import com.example.school.repositories.StudentRepository;
import com.example.school.entities.Teacher;
import com.example.school.repositories.TeacherRepository;
import com.example.school.mappers.CourseMapper;
import com.example.school.repositories.CourseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<CourseResponse> getCourses() {
        return repository.findAll().stream()
                         .map(CourseMapper::toResponse)
                         .toList();
    }

    public CourseResponse getCourseById(long id) {
        return repository.findById(id)
                         .map(CourseMapper::toResponse)
                         .orElseThrow(() -> new EntityNotFoundException("Course not found"));
    }

    public CourseResponse saveCourse(CourseRequest request) {
        Course course = CourseMapper.toEntity(request);

        if (request.teacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.teacherId())
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
            course.setTeacher(teacher);
        }

        if (request.studentIds() != null && !request.studentIds().isEmpty()) {
            Set<Student> students = new HashSet<>();
            for (Long id : request.studentIds()) {
                Student s = studentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Student not found: " + id));
                students.add(s);
            }
            course.setStudents(students);
        }

        return CourseMapper.toResponse(repository.save(course));
    }

    public void updateCourse(long id, CourseRequest request) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.setName(request.name());

        if (request.teacherId() != null) {
            Teacher teacher = teacherRepository.findById(request.teacherId())
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
            course.setTeacher(teacher);
        } else {
            course.setTeacher(null);
        }

        if (request.studentIds() != null) {
            Set<Student> students = new HashSet<>();
            for (Long sId : request.studentIds()) {
                Student s = studentRepository.findById(sId)
                        .orElseThrow(() -> new EntityNotFoundException("Student not found: " + sId));
                students.add(s);
            }
            course.setStudents(students);
        } else {
            course.getStudents().clear();
        }

        repository.save(course);
    }

    public void deleteCourse(long id) {
        if (repository.existsById(id)) repository.deleteById(id);
        else throw new EntityNotFoundException("Course not found");
    }

    public List<CourseResponse> getCoursesByTeacher(long teacherId) {
        return repository.findByTeacherId(teacherId).stream()
                         .map(CourseMapper::toResponse)
                         .toList();
    }
}
