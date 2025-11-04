package com.example.school.controllers; // Ajuste o pacote

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

import com.example.school.dtos.CourseRequest;
import com.example.school.dtos.CourseResponse;
import com.example.school.services.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("courses") // Endpoint principal
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getCourses() {
        return ResponseEntity.ok(service.getCourses());
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable long id) {
        return ResponseEntity.ok(service.getCourseById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourseById(@PathVariable long id) {
        service.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }

   @PostMapping
    public ResponseEntity<CourseResponse> saveCourse(@Valid @RequestBody CourseRequest request) {
        CourseResponse response = service.saveCourse(request);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location)
                             .body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateCourse(@PathVariable long id,
                                             @Valid @RequestBody CourseRequest request) {
        service.updateCourse(request, id);
        return ResponseEntity.noContent().build();
    }
}