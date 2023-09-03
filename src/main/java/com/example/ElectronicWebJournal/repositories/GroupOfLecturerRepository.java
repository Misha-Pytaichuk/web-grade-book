package com.example.ElectronicWebJournal.repositories;


import com.example.ElectronicWebJournal.models.GroupsOfLecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupOfLecturerRepository extends JpaRepository<GroupsOfLecturer, Integer>{
}
