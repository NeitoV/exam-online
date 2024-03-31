package com.exam.data.mapper;

import com.exam.data.dto.result.ResultDetailsDTO;
import com.exam.data.dto.result.StudentResultDTO;
import com.exam.data.enity.ExamResult;
import com.exam.data.enity.ExamResultDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ExamResultMapper {
    @Mapping(source = "id", target = "examResultId")
    @Mapping(source = "exam.code", target = "examCode")
    @Mapping(source = "exam.name", target = "examName")
    @Mapping(source = "exam.lecturer.name", target = "lecturerName")
    @Mapping(source = "student.studentClass.name", target = "className")
    @Mapping(source = "point", target = "point")
    StudentResultDTO examResultToStudentResultDto(ExamResult entity);

    @Mapping(source = "question", target = "questionDTO")
    ResultDetailsDTO toDetailsDTO(ExamResultDetails examResultDetails);
}
