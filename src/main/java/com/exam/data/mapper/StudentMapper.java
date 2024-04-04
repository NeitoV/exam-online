package com.exam.data.mapper;

import com.exam.data.dto.AllowedExamDTO;
import com.exam.data.dto.StudentDTO;
import com.exam.data.dto.result.StudentResultDTO;
import com.exam.data.enity.AllowedStudent;
import com.exam.data.enity.ExamResult;
import com.exam.data.enity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "studentClass.name", target = "className")
    @Mapping(source = "user.id", target = "userId")
    StudentDTO toDTO(Student student);

    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "exam.code", target = "examCode")
    @Mapping(source = "exam.name", target = "examName")
    @Mapping(source = "exam.lecturer.name", target = "lecturerName")
    @Mapping(source = "student.studentClass.name", target = "className")
    AllowedExamDTO toAllowedExamDTO(AllowedStudent allowedStudent);
}
