package com.example.ElectronicWebJournal.controllers;

import com.example.ElectronicWebJournal.dto.group.GroupDTO;
import com.example.ElectronicWebJournal.models.Group;
import com.example.ElectronicWebJournal.security.PersonDetails;
import com.example.ElectronicWebJournal.util.convertors.ConverterToDTO;
import com.example.ElectronicWebJournal.util.exceptions.FindPersonException;
import com.example.ElectronicWebJournal.util.exceptions.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {
    private final ConverterToDTO converterToDTO;

    @GetMapping("/allStudents")
    public Set<GroupDTO> getGroup(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Set<GroupDTO> groupDTOS = new HashSet<>();

        for (Group group: personDetails.getPerson().getGroups()){
            GroupDTO groupDTO = converterToDTO.convertToGroupDTO(group);
            groupDTOS.add(groupDTO);
        }

        return groupDTOS;
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> personExceptionHandler(FindPersonException findPersonException){
        ResponseException responseException = new ResponseException(
                "User not found",
                LocalDateTime.now()
        );


        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
}
