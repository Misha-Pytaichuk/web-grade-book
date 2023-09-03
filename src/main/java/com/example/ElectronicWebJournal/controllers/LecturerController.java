package com.example.ElectronicWebJournal.controllers;

import com.example.ElectronicWebJournal.dto.grade.GradePostDTO;
import com.example.ElectronicWebJournal.dto.grade.GradeUpdateDTO;
import com.example.ElectronicWebJournal.dto.group.GroupDTO;
import com.example.ElectronicWebJournal.dto.task.SetTaskDTO;
import com.example.ElectronicWebJournal.dto.util_for_registration.GroupSubjectRegDTO;
import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.models.GroupsOfLecturer;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.security.PersonDetails;
import com.example.ElectronicWebJournal.services.GradeService;
import com.example.ElectronicWebJournal.services.GroupOfLecturerService;
import com.example.ElectronicWebJournal.services.TaskService;
import com.example.ElectronicWebJournal.util.convertors.ConverterToDTO;
import com.example.ElectronicWebJournal.util.convertors.ConverterToEntity;
import com.example.ElectronicWebJournal.util.exceptions.BindingExceptionHandler;
import com.example.ElectronicWebJournal.util.exceptions.FindException;
import com.example.ElectronicWebJournal.util.exceptions.ResponseException;
import com.example.ElectronicWebJournal.util.exceptions.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lecturer")
public class LecturerController {

        private final BindingExceptionHandler bindingExceptionHandler;
        private final GradeService gradeService;
        private final ConverterToEntity converterToEntity;
        private final TaskService taskService;
        private final ConverterToDTO converterToDTO;
        private final GroupOfLecturerService groupOfLecturerService;

    @GetMapping("/dashboard")
    public Set<GroupDTO> groupDTO(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Person person = personDetails.getPerson();

        Set<GroupsOfLecturer> groupsOfLecturers = person.getGroupsOfLecturers();
        Set<Group> groupsSet = new HashSet<>();
        Set<GroupDTO> groups = new HashSet<>();

        for (GroupsOfLecturer gol: groupsOfLecturers){
            System.out.println(gol.getGroupOfLecturer().getName());
            Group group = gol.getGroupOfLecturer();
            group.getSubjectSet().clear();
            groupsSet.add(group);
        }

        for (Group group: groupsSet){

            for (GroupsOfLecturer gol: groupsOfLecturers){
                if(group.getId() == gol.getGroupOfLecturer().getId()){
                    System.out.println(gol.getSubjectOfLecturer().getSubjectName());

                    group.getSubjectSet().add(gol.getSubjectOfLecturer());
                }
            }
            groups.add(converterToDTO.convertToGroupDTO(group));
        }

        return groups;
    }


    @PostMapping("/addSubjectForLecturer")
    public ResponseEntity<HttpStatus> addSubjectForLecturer(@RequestBody GroupSubjectRegDTO regDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Person person = personDetails.getPerson();

        groupOfLecturerService.save(person, Integer.parseInt(regDTO.getSubjectId()), Integer.parseInt(regDTO.getGroupId()));


        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/grade")
    public ResponseEntity<HttpStatus> gradeStudent(@RequestBody @Valid GradePostDTO gradePostDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        gradeService.save(converterToEntity.convertToGrade(gradePostDTO), gradePostDTO.getPersonId(), gradePostDTO.getSubjectId(), gradePostDTO.getTaskId());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/updateGrade")
    public ResponseEntity<HttpStatus> updateGrade(@RequestBody @Valid GradeUpdateDTO gradeUpdateDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        if(gradeUpdateDTO.getValue() == null) {
            gradeService.delete(gradeUpdateDTO.getId());
            return ResponseEntity.ok(HttpStatus.OK);
        }

        gradeService.update(gradeUpdateDTO.getId(), gradeUpdateDTO.getValue());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/saveTask")
    public ResponseEntity<HttpStatus> saveTask(@RequestBody @Valid SetTaskDTO taskDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        taskService.save(converterToEntity.convertToTask(taskDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/updateTask")
    public ResponseEntity<HttpStatus> updateTask(@RequestBody @Valid SetTaskDTO taskDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        taskService.save(converterToEntity.convertToTask(taskDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> personExceptionHandler(ValidationException validationException){
        ResponseException responseException = new ResponseException(
                validationException.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> personExceptionHandler(FindException findException){
        ResponseException responseException = new ResponseException(
                "Element not found",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
}
