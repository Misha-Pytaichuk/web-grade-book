package com.example.ElectronicWebJournal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "person_subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupsOfLecturer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person lecturer;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subjectOfLecturer;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group groupOfLecturer;

    public GroupsOfLecturer(Person lecturer, Subject subjectOfLecturer, Group groupOfLecturer) {
        this.lecturer = lecturer;
        this.subjectOfLecturer = subjectOfLecturer;
        this.groupOfLecturer = groupOfLecturer;
    }
}