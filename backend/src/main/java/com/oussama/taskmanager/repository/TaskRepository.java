package com.oussama.taskmanager.repository;

import com.oussama.taskmanager.model.Task;
import com.oussama.taskmanager.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByUser(User user);
}
