package com.exam.data.mapper;

import com.exam.data.dto.exam.ExamBasicInformationDTO;
import com.exam.data.dto.result.StudentResultDTO;
import com.exam.data.dto.exam.ExamDTO;
import com.exam.data.dto.exam.ExamShowDTO;
import com.exam.data.enity.Exam;
import com.exam.data.enity.ExamResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    @Mapping(target = "id", ignore = true)
    Exam toEntity(ExamDTO examDTO);

    @Mapping(source = "exam", target = "examDTO.examBasicInformationDTO")
    ExamShowDTO toDTOShow(Exam exam);

    ExamBasicInformationDTO toDTOBasic(Exam exam);

    @Mapping(source = "exam", target = "examBasicInformationDTO")
    ExamDTO toDTO(Exam exam);
}
