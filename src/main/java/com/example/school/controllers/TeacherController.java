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
    public ResponseEntity<List<TeacherResponse>> getTeachers() {
        return ResponseEntity.ok(service.getTeachers());
    }

    @GetMapping("{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable long id) {
        return ResponseEntity.ok(service.getTeacherById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable long id) {
        service.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }

   @PostMapping
    public ResponseEntity<TeacherResponse> saveTeacher(@Valid @RequestBody TeacherRequest request) {
        TeacherResponse response = service.saveTeacher(request);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location)
                             .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTeacher(@PathVariable long id,
                                             @Valid @RequestBody TeacherRequest request) {
        service.updateTeacher(request, id);
        return ResponseEntity.noContent().build();
    }
}