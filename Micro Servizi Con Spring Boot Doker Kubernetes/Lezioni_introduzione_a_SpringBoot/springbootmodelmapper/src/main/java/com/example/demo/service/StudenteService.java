package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.StudenteDto;


public interface StudenteService {
	public StudenteDto getStudenteById(Long userId);

	public StudenteDto createStudente(StudenteDto s);

	public List<StudenteDto> getAllStudents();

}
