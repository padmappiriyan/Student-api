package com.example.studentapi.controllers;

import com.example.studentapi.model.Course;
import com.example.studentapi.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Create a course
    @PostMapping("/course")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) throws ExecutionException, InterruptedException {
        Course created = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get course by ID
    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable String id) throws Throwable {
        Course c = courseService.getCourseById(id);
        return ResponseEntity.ok(c);
    }

    // Get all courses
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // Update course
    @PutMapping("/course")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) throws Throwable {
        Course updated = courseService.updateCourse(course);
        return ResponseEntity.ok(updated);
    }

    // Delete course
    @DeleteMapping("/course/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) throws Throwable {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}

