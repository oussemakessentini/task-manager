package com.oussama.taskmanager.service;

import com.oussama.taskmanager.model.Task;
import com.oussama.taskmanager.model.User;
import com.oussama.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task, User user) {
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getUserTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }
}
