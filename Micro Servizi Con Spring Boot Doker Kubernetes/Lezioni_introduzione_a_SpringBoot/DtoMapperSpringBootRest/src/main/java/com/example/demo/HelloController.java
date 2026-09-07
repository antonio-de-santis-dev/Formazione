package com.example.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	// definito in DtoMapperSpringBootRestApplication
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("findById")
	public StudenteDto findById() {
		Studente s = new Studente();
		s.setCognome("pagano");
		s.setNome("antonio");
		s.setMatricola(1);
		
		StudenteDto sDto = convertToDto(s);
		
		return sDto;
		
		
	}

	@GetMapping("findAll")
	public List<StudenteDto> findAll() {
		Studente s = new Studente();
		s.setCognome("pagano");
		s.setNome("antonio");
		s.setMatricola(1);

		Studente s1 = new Studente();
		s1.setCognome("rossi");
		s1.setNome("mario");
		s1.setMatricola(2);

		List<Studente> res = new ArrayList<>();
		res.add(s1);
		res.add(s);

		List<StudenteDto> resDto = new ArrayList<>();
	
		for (Studente stud : res) {
			StudenteDto sDto = convertToDto(stud);
			resDto.add(sDto);
			
		}
		//return resDto;
		
        return res.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
	
	}
	
//	{
//		"nome": "antonio",
//		"cognome": "pagano"
//	}
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public StudenteDto create(@RequestBody StudenteDto sDto) {
        Studente post = convertToEntity(sDto);
        post.setMatricola(44);
        return convertToDto(post);
    }

	private StudenteDto convertToDto(Studente s) {
		StudenteDto sDto = modelMapper.map(s, StudenteDto.class);
		return sDto;
	}

	private Studente convertToEntity(StudenteDto sDto) {
		Studente Studente = modelMapper.map(sDto, Studente.class);
		return Studente;
	}
}
