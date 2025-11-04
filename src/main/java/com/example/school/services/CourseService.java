package com.example.school.services; // Ajuste o pacote

import java.util.HashSet; // Adicionado para inicializar Sets
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Mantido para segurança, mas geralmente pode ser removido se o @Service tiver a configuração padrão

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

    // --- SAVE COURSE (Estrutura parecida com saveProduct) ---
    @Transactional
    public CourseResponse saveCourse(CourseRequest request) {
        Course course = CourseMapper.toEntity(request);
        
        // Associa Teachers (Usando o loop forEach do modelo ProductService)
        if (request.teacherIds() != null && !request.teacherIds().isEmpty()) {
            Set<Teacher> teachers = new HashSet<>();
            for (Long teacherId : request.teacherIds()) {
                Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found: " + teacherId));
                teachers.add(teacher);
            }
            course.setTeachers(teachers);
        }
        
        // Associa Students (Usando o loop forEach do modelo ProductService)
        if (request.studentIds() != null && !request.studentIds().isEmpty()) {
            Set<Student> students = new HashSet<>();
            for (Long studentId : request.studentIds()) {
                Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found: " + studentId));
                students.add(student);
            }
            course.setStudents(students);
        }
        
        Course savedCourse = repository.save(course);
        return CourseMapper.toDTO(savedCourse);
    }

    // --- UPDATE COURSE (Estrutura parecida com updateProduct) ---
    @Transactional
    public void updateCourse(CourseRequest request, long id) {
        Course course = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        
        course.setName(request.name());
        
        // Atualiza Teachers
        if (request.teacherIds() != null) {
            Set<Teacher> teachers = new HashSet<>();
            for (Long teacherId : request.teacherIds()) {
                Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new EntityNotFoundException("Teacher not found: " + teacherId));
                teachers.add(teacher);
            }
            course.setTeachers(teachers);
        } else {
            // Se tagIds fosse null, no ProductService ele limpa as tags.
            // Aqui fazemos o mesmo para manter a consistência do PUT.
            course.getTeachers().clear(); 
        }
        
        // Atualiza Students
        if (request.studentIds() != null) {
            Set<Student> students = new HashSet<>();
            for (Long studentId : request.studentIds()) {
                Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found: " + studentId));
                students.add(student);
            }
            course.setStudents(students);
        } else {
            // Limpa os estudantes se a lista vier null (consistente com o modelo)
            course.getStudents().clear(); 
        }
        
        repository.save(course);
    }
}