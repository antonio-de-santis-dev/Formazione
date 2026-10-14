package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.StudenteDto;
import com.example.demo.model.Studente;

@Mapper
public interface AutoStudenteMapper {

	AutoStudenteMapper MAPPER = Mappers.getMapper(AutoStudenteMapper.class);

    StudenteDto mapToStudenteDto(Studente s);

    Studente mapToStudente(StudenteDto sDto);
}
