package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.StudenteDto;
import com.example.demo.mapper.AutoStudenteMapper;
import com.example.demo.mapper.StudenteMapper;
import com.example.demo.model.Studente;
import com.example.demo.service.StudenteService;

@Service
public class StudenteServiceImpl implements StudenteService {

	// mappato come bean nella classe di avvio di
	// springboot
	@Autowired
	private ModelMapper modelMapper;


	@Override
	public StudenteDto getStudenteById(Long StudenteId) {
		//simuliamo una ricerca sul database
		Studente s = new Studente();
		s.setCognome("Pagano");
		s.setNome("Antonio");

		// return StudenteMapper.mapToStudenteDto(s);
		// return modelMapper.map(s, StudenteDto.class);
		return AutoStudenteMapper.MAPPER.mapToStudenteDto(s);

	}

	@Override
	public StudenteDto createStudente(StudenteDto s) {
		// Convert StudenteDto into Studente JPA Entity
		// Studente Studente = StudenteMapper.mapToStudente(s);

		// Studente Studente = modelMapper.map(s, Studente.class);

		Studente savedStudente = AutoStudenteMapper.MAPPER.mapToStudente(s);
		
		//ipotizziamo di salvarlo sul db
		
		// Convert Studente JPA entity to StudenteDto
//		 StudenteDto savedStudenteDto =
//		 StudenteMapper.mapToStudenteDto(savedStudente);

//		 StudenteDto savedStudenteDto = modelMapper.map(savedStudente,
//		 StudenteDto.class);

		StudenteDto savedStudenteDto = AutoStudenteMapper.MAPPER.mapToStudenteDto(savedStudente);

		return savedStudenteDto;

	}

	@Override
	public List<StudenteDto> getAllStudents() {
		List<Studente> studenteS = new ArrayList<>();
		Studente s = new Studente();
		s.setCognome("Pagano");
		s.setNome("Antonio");

		Studente s1 = new Studente();
		s1.setCognome("Pagano");
		s1.setNome("Antonio");

		Studente s2 = new Studente();
		s2.setCognome("Pagano");
		s2.setNome("Antonio");
		studenteS.add(s);
		studenteS.add(s1);
		studenteS.add(s2);

//      return studenteS.stream().map(StudenteMapper::mapToStudenteDto)
//              .collect(Collectors.toList());

//      return studenteS.stream().map((Studente) -> modelMapper.map(Studente, StudenteDto.class))
//              .collect(Collectors.toList());

		return studenteS.stream().map((Studente) -> AutoStudenteMapper.MAPPER.mapToStudenteDto(Studente))
				.collect(Collectors.toList());

	}

}
