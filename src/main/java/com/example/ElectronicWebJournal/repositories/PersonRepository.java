package com.example.ElectronicWebJournal.repositories;

import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("select distinct p from Person p " +
            "join fetch p.groups gro " +
            "where p.email = :email")
    Optional<Person> findPersonByEmail(@Param("email") String email);

    @Query("select distinct p from Person p " +
            "where p.groups = :group")
    List<Person> findStudents(@Param("group") Group group);


    Optional<Person> findPersonByTelephoneNumber(String num);

    Optional<Person> findPersonById(int id);

}
