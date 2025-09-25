package com.example.studentapi.controllers;

import com.example.studentapi.model.Student;
import com.example.studentapi.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class StudentController {

    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws ExecutionException, InterruptedException {
        Student created = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id) throws Throwable {
        Student s = studentService.getStudentById(id);
        return ResponseEntity.ok(s);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping("/student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) throws Throwable {
        Student updated = studentService.updateStudent(student);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) throws Throwable {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}

