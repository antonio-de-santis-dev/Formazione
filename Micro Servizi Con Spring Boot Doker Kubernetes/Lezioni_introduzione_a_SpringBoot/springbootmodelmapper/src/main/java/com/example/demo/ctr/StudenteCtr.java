package com.example.demo.ctr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StudenteDto;
import com.example.demo.service.StudenteService;


@RestController
@RequestMapping("api/studenti")
public class StudenteCtr {
   
		@Autowired	
	   private StudenteService studenteService;

	 //http://localhost:8080/api/studenti/1  
    @GetMapping("{id}")
    public ResponseEntity<StudenteDto> getStudenteById(@PathVariable("id") Long id){

    	StudenteDto s = studenteService.getStudenteById(id);
    	
    	
    	return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudenteDto> createStudente(@RequestBody StudenteDto sDto){
        StudenteDto savedStudente = studenteService.createStudente(sDto);
        return new ResponseEntity<>(savedStudente, HttpStatus.CREATED);
    }

  
    @GetMapping
    public ResponseEntity<List<StudenteDto>> getAllStudents(){
        List<StudenteDto> studenteS = studenteService.getAllStudents();
        return new ResponseEntity<>(studenteS, HttpStatus.OK);
    }
	
}

