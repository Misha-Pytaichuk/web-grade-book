package com.example.ElectronicWebJournal.controllers;

import com.example.ElectronicWebJournal.dto.group.GetGroupDTO;
import com.example.ElectronicWebJournal.dto.task.SetTaskDTO;
import com.example.ElectronicWebJournal.dto.task.TaskDTO;
import com.example.ElectronicWebJournal.services.GroupService;
import com.example.ElectronicWebJournal.services.TaskService;
import com.example.ElectronicWebJournal.util.convertors.ConverterToDTO;
import com.example.ElectronicWebJournal.util.convertors.ConverterToEntity;
import com.example.ElectronicWebJournal.util.exceptions.BindingExceptionHandler;
import com.example.ElectronicWebJournal.util.exceptions.ResponseException;
import com.example.ElectronicWebJournal.util.exceptions.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/util")
@RequiredArgsConstructor
public class UtilController {
    private final GroupService groupService;
    private final ConverterToDTO converterToDTO;


    @GetMapping("/groups")
    public List<GetGroupDTO> getAllGroups(){
        return groupService.findAll().stream().map(converterToDTO::convertToGetGroupDTO).collect(Collectors.toList());
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> personExceptionHandler(ValidationException validationException){
        ResponseException responseException = new ResponseException(
                validationException.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
}
