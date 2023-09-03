package com.example.ElectronicWebJournal.repositories;

import com.example.ElectronicWebJournal.models.Grade;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findAllByPerson(Person person);

     @Query("select g from Grade g " +
             "left join fetch g.comments c " +
             "left join fetch c.personOwnerComment cp " +
             "where g.person.id = :personId and g.subject.id = :subjectId")
    List<Grade> findGradesByPersonAndSubject(@Param("personId") int personId, @Param("subjectId") int subjectId);

}
