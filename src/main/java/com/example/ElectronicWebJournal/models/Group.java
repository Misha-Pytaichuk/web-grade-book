package com.example.ElectronicWebJournal.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "`group`")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`name`")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> peopleGroups = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_subjects",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectSet;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<Task> taskSet;

    @OneToMany(mappedBy = "groupOfLecturer", fetch = FetchType.EAGER)
    private Set<GroupsOfLecturer> groupsOfLecturers;

    public Group(String name) {
        this.name = name;
    }
}
