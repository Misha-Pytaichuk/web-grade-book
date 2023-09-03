package com.example.ElectronicWebJournal.util.convertors;


import com.example.ElectronicWebJournal.dto.comment.CommentGetDTO;
import com.example.ElectronicWebJournal.dto.grade.GradeGetDTO;
import com.example.ElectronicWebJournal.dto.grade.GradesIdDTO;
import com.example.ElectronicWebJournal.dto.group.GetGroupDTO;
import com.example.ElectronicWebJournal.dto.group.GroupDTO;
import com.example.ElectronicWebJournal.dto.person.PersonStudentInfoDTO;
import com.example.ElectronicWebJournal.dto.person.PersonUserInfoDTO;
import com.example.ElectronicWebJournal.dto.subject.SubjectDTO;
import com.example.ElectronicWebJournal.dto.task.TaskDTO;
import com.example.ElectronicWebJournal.models.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConverterToDTO {
    private final ModelMapper modelMapper;

    public Object convertToPersonDTO(Person person, Class<?> clazz){
        if(clazz == PersonStudentInfoDTO.class){
            return modelMapper.map(person, clazz);
        } else if(clazz == PersonUserInfoDTO.class){
            return modelMapper.map(person, clazz);
        }
        else return null;
    }

    public GroupDTO convertToGroupDTO(Group group){
        GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
        groupDTO.setPeopleGroups(group.getPeopleGroups().stream().map(person -> (PersonStudentInfoDTO) convertToPersonDTO(person, PersonStudentInfoDTO.class)).collect(Collectors.toSet()));
        groupDTO.setSubjectSet(group.getSubjectSet().stream().map(this::convertToSubjectDTO).collect(Collectors.toSet()));
        groupDTO.setTaskSet(group.getTaskSet().stream().map(this::convertToTaskDTO).collect(Collectors.toSet()));
        return groupDTO;
    }

    public TaskDTO convertToTaskDTO(Task task){
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        taskDTO.setGradeSet(task.getGradeSet().stream().map(grade -> (GradesIdDTO) convertToGradeDTO(grade, GradesIdDTO.class)).collect(Collectors.toSet()));
        return taskDTO;
    }

    public GetGroupDTO convertToGetGroupDTO(Group group){
        return modelMapper.map(group, GetGroupDTO.class);
    }

    public SubjectDTO convertToSubjectDTO(Subject subject){
        SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
        subjectDTO.setGradeList(subject.getGradeList().stream().map(grade -> (GradeGetDTO) convertToGradeDTO(grade, GradeGetDTO.class)).collect(Collectors.toSet()));
        return modelMapper.map(subject, SubjectDTO.class);
    }

    public Object convertToGradeDTO(Grade grade, Class<?> clazz){
        if(clazz == GradeGetDTO.class){
            GradeGetDTO gradeGetDTO = modelMapper.map(grade, GradeGetDTO.class);
            gradeGetDTO.setComments(grade.getComments().stream().map(this::convertToCommentDTO).collect(Collectors.toSet()));
            return gradeGetDTO;
        } else {
            return modelMapper.map(grade, GradesIdDTO.class);
        }
    }
    public CommentGetDTO convertToCommentDTO(Comment comment){
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        CommentGetDTO commentGetDTO = modelMapper.map(comment, CommentGetDTO.class);
        commentGetDTO.setPersonOwnerId(comment.getPersonOwnerComment().getId());
        System.out.println("\n\n lol"+comment.getPersonOwnerComment().getFirstname());

        return commentGetDTO;
    }
}
