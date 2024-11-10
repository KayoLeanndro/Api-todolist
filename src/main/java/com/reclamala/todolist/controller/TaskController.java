package com.reclamala.todolist.controller;

import com.reclamala.todolist.models.Task;
import com.reclamala.todolist.service.FirestoreService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private FirestoreService firestoreService;

    @PostMapping("/createTask")
    public String createTask(@RequestBody Task task) {
        try {
            return firestoreService.saveTask(task);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating task: " + e.getMessage();
        }
    }

    @GetMapping("/getAllTasks")
    public List<Task> getAllTasks() {
        try {
            return firestoreService.getAllTasks();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // @DeleteMapping("/deleteTask")
    // public void deleteTask(@RequestParam String taskId) {
    //     try {
    //         firestoreService.deleteTask(taskId);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

}
