package com.example.ElectronicWebJournal.models;

import com.example.ElectronicWebJournal.util.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "telephone_number", unique = true, nullable = false)
    private String telephoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`role`")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "peopleGroups", fetch = FetchType.LAZY)
    private Set<Group> groups = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<Grade> grades;

    @JsonIgnore
    @OneToMany(mappedBy = "personOwnerComment", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "lecturer", fetch = FetchType.EAGER)
    private Set<GroupsOfLecturer> groupsOfLecturers;

    public Person(String firstname, String secondName, String lastname, String telephoneNumber, String email, String password) {
        this.firstname = firstname;
        this.secondName = secondName;
        this.lastname = lastname;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.password = password;
    }

    public Person(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Person(String email) {
        this.email = email;
    }
}
