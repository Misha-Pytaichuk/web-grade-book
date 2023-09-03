package com.example.ElectronicWebJournal;

import com.example.ElectronicWebJournal.util.convertors.ConverterToDTO;
import com.example.ElectronicWebJournal.util.convertors.ConverterToEntity;
import com.example.ElectronicWebJournal.util.exceptions.BindingExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElectronicWebJournalApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElectronicWebJournalApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public BindingExceptionHandler bindingExceptionHandler(){
		return new BindingExceptionHandler();
	}

	@Bean
	public ConverterToDTO converterToDTO(){return new ConverterToDTO(modelMapper());}

	@Bean
	public ConverterToEntity converterToEntity(){return new ConverterToEntity(modelMapper());}
}
