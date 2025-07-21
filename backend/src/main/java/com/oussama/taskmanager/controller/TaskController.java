package com.oussama.taskmanager.controller;

import com.oussama.taskmanager.model.Task;
import com.oussama.taskmanager.model.User;
import com.oussama.taskmanager.service.TaskService;
import com.oussama.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(taskService.createTask(task, user));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(taskService.getUserTasks(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id,
                                       @RequestBody Task updatedTask,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        Task existing = taskService.getById(id);
        User user = userService.findByUsername(userDetails.getUsername());

        if (!existing.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build(); // forbid update of other users' tasks
        }

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setStatus(updatedTask.getStatus());

        return ResponseEntity.ok(taskService.updateTask(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        Task task = taskService.getById(id);
        User user = userService.findByUsername(userDetails.getUsername());

        if (!task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build(); // forbid deletion of others' tasks
        }

        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

}
