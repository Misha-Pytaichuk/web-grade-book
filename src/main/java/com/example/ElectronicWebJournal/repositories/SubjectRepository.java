package com.example.ElectronicWebJournal.repositories;

import com.example.ElectronicWebJournal.models.Grade;
import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
 @Query("SELECT g from Grade g where g.person = :person and  g.subject = :subject")
 List<Grade> findGradesBySubject(@Param("person")Person person, @Param("subject") Subject subject);

      List<Subject> findAllByGroupListContaining(Group group);
//    List<Subject> findAllByGroupList(List<Group> group);

 }
