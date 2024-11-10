package com.reclamala.todolist.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.reclamala.todolist.models.Task;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestoreService {

    @Autowired
    private Firestore firestore;

    public String saveTask(Task task) throws Exception {
        task.setCreatedAt(new Date());
        task.setUpdatedAt(null);

        CollectionReference tasks = firestore.collection("tasks");

        ApiFuture<DocumentReference> addedDocRef = tasks.add(task);

        String generatedId = addedDocRef.get().getId();

        task.setId(generatedId);

        tasks.document(generatedId).set(task);

        return generatedId;
    }

    public List<Task> getAllTasks() throws Exception {
        CollectionReference tasks = firestore.collection("tasks");

        ApiFuture<QuerySnapshot> future = tasks.get();

        QuerySnapshot querySnapshot = future.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        System.out.println("Número de documentos encontrados: " + documents.size());

        List<Task> taskList = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            taskList.add(document.toObject(Task.class));
        }
        return taskList;
    }

    // public void deleteTask(String taskId) throws Exception {

    //     CollectionReference tasks = firestore.collection("tasks");

    //     DocumentReference taskRef = tasks.document(taskId);

    //     ApiFuture<WriteResult> deleteFuture = taskRef.delete();

    //     deleteFuture.get();

    //     System.out.println("Tarefa com ID " + taskId + " foi excluída com sucesso.");

    // }

}
