package com.example.ElectronicWebJournal.services;

import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.repositories.GroupRepository;
import com.example.ElectronicWebJournal.util.exceptions.FindException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {
    private final GroupRepository groupRepository;
    public List<Group> findAll(){
        return groupRepository.findAll();
    }
    public Group findById(int id){
        return groupRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Group group){
        groupRepository.save(group);
    }

    @Transactional
    public void update(Group group){
        groupRepository.findById(group.getId()).orElseThrow(FindException::new);
        groupRepository.save(group);
    }

    @Transactional
    public void delete(int id){
        groupRepository.deleteById(id);
    }
}
