package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.models.Grade;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.models.Task;
import com.example.ElectronicWebJournal.repositories.GradeRepository;
import com.example.ElectronicWebJournal.repositories.PersonRepository;
import com.example.ElectronicWebJournal.repositories.SubjectRepository;
import com.example.ElectronicWebJournal.repositories.TaskRepository;
import com.example.ElectronicWebJournal.util.exceptions.FindPersonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;

    public Grade findById(int id){
        return gradeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Grade grade, Integer personId, Integer subjectId, Integer taskId){
        Person person = personRepository.findById(personId).orElseThrow(FindPersonException::new);
        grade.setPerson(person);
        grade.setSubject(subjectRepository.findById(subjectId).orElse(null));

        if(taskId == null) {
            Task task = new Task();
            task.setTaskDate(LocalDateTime.now());
            taskRepository.save(task);
            taskId = task.getId();
        }

        Task task = taskRepository.findById(taskId).orElse(null);

        grade.setTask(task);

        enrichGrade(grade);

        person.getGrades().add(grade);
        gradeRepository.save(grade);

        assert task != null;
        task.getGradeSet().add(grade);
    }

    @Transactional
    public void update(int gradeId, double value){
        Grade grade = gradeRepository.findById(gradeId).orElse(null);
        Objects.requireNonNull(grade).setValue(value);
        grade.setUpdatedAt(LocalDateTime.now());
        gradeRepository.save(grade);
    }

    @Transactional
    public void delete(int gradeId){
        Grade grade = gradeRepository.findById(gradeId).orElse(null);
        if(grade != null){
            grade.getTask().getGradeSet().remove(grade);
            gradeRepository.delete(grade);
        }

    }

    private void enrichGrade(Grade grade){
        grade.setCreatedAt(LocalDateTime.now());
        grade.setUpdatedAt(grade.getCreatedAt());
    }
}
