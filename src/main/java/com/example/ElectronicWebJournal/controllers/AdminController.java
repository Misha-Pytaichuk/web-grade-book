package com.example.ElectronicWebJournal.controllers;

import com.example.ElectronicWebJournal.dto.group.SetGroupDTO;
import com.example.ElectronicWebJournal.dto.group.UpdateGroupDTO;
import com.example.ElectronicWebJournal.dto.role.RoleDTO;
import com.example.ElectronicWebJournal.dto.subject.SetSubjectDTO;
import com.example.ElectronicWebJournal.dto.subject.UpdateSubjectDTO;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.security.PersonDetails;
import com.example.ElectronicWebJournal.services.GroupService;
import com.example.ElectronicWebJournal.services.PersonService;
import com.example.ElectronicWebJournal.services.SubjectService;
import com.example.ElectronicWebJournal.util.Roles;
import com.example.ElectronicWebJournal.util.convertors.ConverterToEntity;
import com.example.ElectronicWebJournal.util.exceptions.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PersonService personService;
    private final BindingExceptionHandler bindingExceptionHandler;
    private final ConverterToEntity converterToEntity;
    private final GroupService groupService;
    private final SubjectService subjectService;

    public static Roles globalRole = Roles.ROLE_STUDENT;

    @PostMapping("/remove/{id}")
    public ResponseEntity<HttpStatus> remove(@PathVariable Integer id){
        Person person = personService.findById(id);
        if(personService.findById(id) == null)
            throw new FindPersonException();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        if(personDetails.getPerson().getId() == id) return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        System.out.println(id);
        personService.delete(person);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/setRole")
    public ResponseEntity<HttpStatus> giveRole(@RequestBody RoleDTO role){

        System.out.println(role.getRole());
        switch (role.getRole()) {
            case "ROLE_STUDENT" -> globalRole = Roles.ROLE_STUDENT;
            case "ROLE_LECTURER" -> globalRole = Roles.ROLE_LECTURER;
            case "ROLE_CURATOR" -> globalRole = Roles.ROLE_CURATOR;
        }

        System.out.println(globalRole);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/createGroup")
    public ResponseEntity<HttpStatus> createGroup(@RequestBody @Valid SetGroupDTO groupDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        groupService.save(converterToEntity.convertToGroup(groupDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/updateGroup")
    public ResponseEntity<HttpStatus> updateGroup(@RequestBody @Valid UpdateGroupDTO groupDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        groupService.update(converterToEntity.convertToGroup(groupDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/deleteGroup")
    public ResponseEntity<HttpStatus> deleteGroup(@RequestBody Integer id){

        if(id == null)
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        else if(groupService.findById(id) == null)
            throw new FindException();

        groupService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/createSubject")
    public ResponseEntity<HttpStatus> createSubject(@RequestBody @Valid SetSubjectDTO subjectDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        subjectService.save(converterToEntity.convertToSubject(subjectDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/updateSubject")
    public ResponseEntity<HttpStatus> updateSubject(@RequestBody @Valid UpdateSubjectDTO subjectDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        subjectService.update(converterToEntity.convertToSubject(subjectDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/deleteSubject")
    public ResponseEntity<HttpStatus> deleteSubject(@RequestBody Integer id){

        if(id == null)
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        else if(subjectService.findById(id) == null)
            throw new FindException();

        subjectService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> adminExceptionHandler(FindPersonException findPersonException){
        ResponseException responseException = new ResponseException(
                "User not found",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> adminExceptionHandler(ValidationException validationException){
        ResponseException responseException = new ResponseException(
                validationException.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> adminExceptionHandler(FindException findException){
        ResponseException responseException = new ResponseException(
                "Element not found",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
}
