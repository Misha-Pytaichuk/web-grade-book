package com.example.ElectronicWebJournal.repositories;

import com.example.ElectronicWebJournal.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
