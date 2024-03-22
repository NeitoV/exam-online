package com.exam.data.mapper;

import com.exam.data.dto.exam.ExamDTO;
import com.exam.data.dto.exam.ExamShowDTO;
import com.exam.data.enity.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    @Mapping(target = "id", ignore = true)
    Exam toEntity(ExamDTO examDTO);

    @Mapping(target = "examDTO.expiryDate", source = "expiryDate")
    @Mapping(target = "examDTO.durationMinutes", source = "durationMinutes")
    @Mapping(target = "examDTO.name", source = "name")
    @Mapping(target = "examDTO.id", source = "id")
    ExamShowDTO toDTOShow(Exam exam);
}
