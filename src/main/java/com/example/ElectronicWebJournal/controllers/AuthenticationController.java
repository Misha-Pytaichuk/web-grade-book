package com.example.ElectronicWebJournal.controllers;


import com.example.ElectronicWebJournal.dto.person.LecturerRegistrationDTO;
import com.example.ElectronicWebJournal.dto.person.PersonAuthenticationDTO;
import com.example.ElectronicWebJournal.dto.person.StudentRegistrationDTO;
import com.example.ElectronicWebJournal.models.Person;
import com.example.ElectronicWebJournal.security.JWTUtil;
import com.example.ElectronicWebJournal.services.auth.RegistrationService;
import com.example.ElectronicWebJournal.util.convertors.ConverterToEntity;
import com.example.ElectronicWebJournal.util.exceptions.*;
import com.example.ElectronicWebJournal.util.validation.RegistrationValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ConverterToEntity converterToEntity;
    private final BindingExceptionHandler bindingExceptionHandler;
    private final AuthenticationManager authenticationManager;
    private final RegistrationValidator registrationValidator;



    @PostMapping("/registrationStudent")
    public Map<String, String> createStudent(@RequestBody @Valid StudentRegistrationDTO studentRegistrationDTO, BindingResult bindingResult) {
        Person person = converterToEntity.convertToPerson(studentRegistrationDTO);
        registrationValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        registrationService.registerStudent(person, studentRegistrationDTO.getGroupId());

        String token = jwtUtil.generateToken(person.getEmail());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/registrationLecturer")
    public Map<String, String> createLecturer(@RequestBody @Valid LecturerRegistrationDTO lecturerRegistrationDTO, BindingResult bindingResult) {
        Person person = converterToEntity.convertToPerson(lecturerRegistrationDTO);
        registrationValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        registrationService.registerLecturer(person);

        String token = jwtUtil.generateToken(person.getEmail());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid PersonAuthenticationDTO personAuthenticationDTO, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingExceptionHandler.ex(bindingResult));
        }

        Person person = converterToEntity.convertToPerson(personAuthenticationDTO);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                personAuthenticationDTO.getEmail(),
                personAuthenticationDTO.getPassword()
        );

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException badCredentialsException){
            throw new FindPersonException();
        }

        String token = jwtUtil.generateToken(person.getEmail());
        return Map.of("jwt-token", token);
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
    private ResponseEntity<ResponseException> personExceptionHandler(FindPersonException findPersonException){
        ResponseException responseException = new ResponseException(
                "email - User not found",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ResponseException> personExceptionHandler(CreateException createException){
        ResponseException responseException = new ResponseException(
                "User already exist",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
}
