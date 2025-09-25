package com.example.studentapi.repository;

import com.example.studentapi.model.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
public class StudentRepository {
    private static final String COLLECTION = "students";

    public Student save(Student student) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        if (student.getId() == null || student.getId().isBlank()) {
            student.setId(UUID.randomUUID().toString());
        }
        ApiFuture<WriteResult> writeResult = db.collection(COLLECTION).document(student.getId()).set(student);
        writeResult.get(); // wait for completion
        return student;
    }

    public Student findById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();
        return doc.exists() ? doc.toObject(Student.class) : null;
    }

    public List<Student> findAll() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Student> students = new ArrayList<>();
        for (QueryDocumentSnapshot d : documents) students.add(d.toObject(Student.class));
        return students;
    }

    public Student update(Student student) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference ref = db.collection(COLLECTION).document(student.getId());
        if (!ref.get().get().exists()) return null;
        ref.set(student).get();
        return student;
    }

    public boolean delete(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference ref = db.collection(COLLECTION).document(id);
        if (!ref.get().get().exists()) return false;
        ref.delete().get();
        return true;
    }
}

