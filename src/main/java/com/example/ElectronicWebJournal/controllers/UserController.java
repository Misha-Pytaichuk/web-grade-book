package com.example.ElectronicWebJournal.controllers;

import com.example.ElectronicWebJournal.dto.comment.CommentPostDTO;
import com.example.ElectronicWebJournal.dto.comment.UpdateCommentDTO;
import com.example.ElectronicWebJournal.dto.person.PersonUserInfoDTO;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.security.PersonDetails;
import com.example.ElectronicWebJournal.services.CommentService;
import com.example.ElectronicWebJournal.services.PersonService;
import com.example.ElectronicWebJournal.util.convertors.ConverterToDTO;
import com.example.ElectronicWebJournal.util.exceptions.BindingExceptionHandler;
import com.example.ElectronicWebJournal.util.exceptions.FindException;
import com.example.ElectronicWebJournal.util.exceptions.FindPersonException;
import com.example.ElectronicWebJournal.util.exceptions.ResponseException;
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
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final PersonService personService;
    private final ConverterToDTO converterToDTO;
    private final BindingExceptionHandler bindingExceptionHandler;
    private final CommentService commentService;

    @PostMapping("/sendComment/{id}")
    public ResponseEntity<HttpStatus> sendComment(@RequestBody @Valid CommentPostDTO comment, @PathVariable int id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingExceptionHandler.ex(bindingResult);
        }

        commentService.save(comment, id, getCurrentPersonFromContext());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/updateComment")
    public ResponseEntity<HttpStatus> sendComment(@RequestBody @Valid UpdateCommentDTO comment, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingExceptionHandler.ex(bindingResult);
        }

        if(comment.getComment() == null){
            commentService.delete(comment.getId());
        }

        commentService.update(comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping
    public PersonUserInfoDTO getUser(){
        return (PersonUserInfoDTO) converterToDTO.convertToPersonDTO(getCurrentPersonFromContext(), PersonUserInfoDTO.class);
    }

    @GetMapping("/{id}")
    public PersonUserInfoDTO getUser(@PathVariable int id){
        return (PersonUserInfoDTO) converterToDTO.convertToPersonDTO(personService.findById(id), PersonUserInfoDTO.class);
    }

    private Person getCurrentPersonFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> personExceptionHandler(FindPersonException findPersonException){
        ResponseException responseException = new ResponseException(
                "User not found",
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
