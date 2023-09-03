package com.example.ElectronicWebJournal.repositories;

import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findGroupsById(int id);
}
