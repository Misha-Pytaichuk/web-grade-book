package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.models.Task;
import com.example.ElectronicWebJournal.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public void save(Task task){
        if(task.getTaskDate() == null) task.setTaskDate(LocalDateTime.now());
        taskRepository.save(task);
    }

}
