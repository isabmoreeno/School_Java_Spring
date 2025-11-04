package com.example.school.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.school.dtos.StudentRequest;
import com.example.school.dtos.StudentResponse;
import com.example.school.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("students")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getStudents() {
        return ResponseEntity.ok(service.getStudents());
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable long id) {
        service.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

   @PostMapping
    public ResponseEntity<StudentResponse> saveStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = service.saveStudent(request);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location)
                             .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateStudent(@PathVariable long id,
                                             @Valid @RequestBody StudentRequest request) {
        service.updateStudent(request, id);
        return ResponseEntity.noContent().build();
    }
}