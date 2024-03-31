package com.exam.data.mapper;

import com.exam.data.dto.StudentDTO;
import com.exam.data.enity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "studentClass.name", target = "className")
    @Mapping(source = "user.id", target = "userId")
    StudentDTO toDTO(Student student);
}
