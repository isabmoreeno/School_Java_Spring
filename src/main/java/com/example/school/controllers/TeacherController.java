package com.example.school.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.school.dtos.TeacherRequest;
import com.example.school.dtos.TeacherResponse;
import com.example.school.services.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("teachers")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService service;

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getAll() {
        return ResponseEntity.ok(service.getTeachers());
    }

    @GetMapping("{id}")
    public ResponseEntity<TeacherResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(service.getTeacherById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> create(@Valid @RequestBody TeacherRequest request) {
        TeacherResponse response = service.saveTeacher(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                  .path("/{id}")
                                                  .buildAndExpand(response.id())
                                                  .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @Valid @RequestBody TeacherRequest request) {
        service.updateTeacher(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
