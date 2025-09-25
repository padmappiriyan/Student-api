package com.example.studentapi.service;

import com.example.studentapi.exception.NotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student createStudent(Student s) throws ExecutionException, InterruptedException {
        return repo.save(s);
    }

    public Student getStudentById(String id) throws Throwable {
        Student s = repo.findById(id);
        if (s == null) throw new Throwable("Student not found with id: " + id);
        return s;
    }

    public List<Student> getAllStudents() throws ExecutionException, InterruptedException {
        return repo.findAll();
    }

    public Student updateStudent(Student s) throws Throwable {
        Student updated = repo.update(s);
        if (updated == null) throw new Throwable("Cannot update. Student not found with id: " + s.getId());
        return updated;
    }

    public void deleteStudent(String id) throws Throwable {
        boolean deleted = repo.delete(id);
        if (!deleted) throw new Throwable("Cannot delete. Student not found with id: " + id);
    }
}
