package com.example.demo.mapper;

import com.example.demo.dto.StudenteDto;
import com.example.demo.model.Studente;

public class StudenteMapper {
	  // Convert Studente JPA Entity into StudenteDto
    public static StudenteDto mapToStudenteDto(Studente s){
        StudenteDto sDto = new StudenteDto(
                s.getNome(),s.getCognome()
        );
        return sDto;
    }

    // Convert StudenteDto into Studente JPA Entity
    public static Studente mapToStudente(StudenteDto sDto){
        Studente s = new Studente(
                sDto.getNome(),
                sDto.getCognome()
        );
        return s;
    }

}
