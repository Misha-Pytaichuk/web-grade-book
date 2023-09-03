package com.example.ElectronicWebJournal.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`subject`")
@Getter
@Setter
@NoArgsConstructor
public class Subject {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "subject_name")
    private String subjectName;

    @ManyToMany(mappedBy = "subjectSet", fetch = FetchType.LAZY)
    private List<Group> groupList;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    private Set<Grade> gradeList;

    @OneToMany(mappedBy = "subjectOfLecturer", fetch = FetchType.EAGER)
    private Set<GroupsOfLecturer> groupsOfLecturers;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }
}
