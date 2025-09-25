package com.example.studentapi.service;

import com.example.studentapi.model.Course;
import com.example.studentapi.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CourseService {

    private final CourseRepository repo;

    public CourseService(CourseRepository repo) {
        this.repo = repo;
    }

    // Create a new course
    public Course createCourse(Course c) throws ExecutionException, InterruptedException {
        return repo.save(c);
    }

    // Get a course by ID
    public Course getCourseById(String id) throws Throwable {
        Course c = repo.findById(id);
        if (c == null) throw new Throwable("Course not found with id: " + id);
        return c;
    }

    // Get all courses
    public List<Course> getAllCourses() throws ExecutionException, InterruptedException {
        return repo.findAll();
    }

    // Update a course
    public Course updateCourse(Course c) throws Throwable {
        Course updated = repo.update(c);
        if (updated == null) throw new Throwable("Cannot update. Course not found with id: " + c.getId());
        return updated;
    }

    // Delete a course
    public void deleteCourse(String id) throws Throwable {
        boolean deleted = repo.delete(id);
        if (!deleted) throw new Throwable("Cannot delete. Course not found with id: " + id);
    }
}

