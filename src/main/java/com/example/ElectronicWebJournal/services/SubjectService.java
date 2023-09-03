package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.models.Subject;
import com.example.ElectronicWebJournal.repositories.SubjectRepository;
import com.example.ElectronicWebJournal.util.exceptions.FindException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Subject findById(int id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Subject subject){
        subjectRepository.save(subject);
    }

    @Transactional
    public void update(Subject subject){
        subjectRepository.findById(subject.getId()).orElseThrow(FindException::new);
        subjectRepository.save(subject);
    }

    @Transactional
    public void delete(int id){
        subjectRepository.deleteById(id);
    }
}
