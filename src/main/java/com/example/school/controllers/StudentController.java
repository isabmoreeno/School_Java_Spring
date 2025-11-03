package com.example.school.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.ok(service.getStudents());
    }

    @GetMapping("{id}")
    public ResponseEntity<StudentResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.getStudentById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = service.saveStudent(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(response.id())
                                                  .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody StudentRequest request) {
        service.updateStudent(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
