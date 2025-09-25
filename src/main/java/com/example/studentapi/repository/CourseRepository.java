package com.example.studentapi.repository;

import com.example.studentapi.model.Course;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
public class CourseRepository {
    private static final String COLLECTION = "courses";

    // Save (Create new or Update if id exists)
    public Course save(Course course) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        if (course.getId() == null || course.getId().isBlank()) {
            course.setId(UUID.randomUUID().toString()); // auto-generate id if missing
        }
        ApiFuture<WriteResult> writeResult = db.collection(COLLECTION).document(course.getId()).set(course);
        writeResult.get(); // wait until write completes
        return course;
    }

    // Get a course by ID
    public Course findById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
        return doc.exists() ? doc.toObject(Course.class) : null;
    }

    // Get all courses
    public List<Course> findAll() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Course> courses = new ArrayList<>();
        for (QueryDocumentSnapshot d : documents) {
            courses.add(d.toObject(Course.class));
        }
        return courses;
    }

    // Update existing course
    public Course update(Course course) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference ref = db.collection(COLLECTION).document(course.getId());
        if (!ref.get().get().exists()) return null; // if not exists, return null
        ref.set(course).get();
        return course;
    }

    // Delete course by ID
    public boolean delete(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference ref = db.collection(COLLECTION).document(id);
        if (!ref.get().get().exists()) return false; // if not exists
        ref.delete().get();
        return true;
    }
}
