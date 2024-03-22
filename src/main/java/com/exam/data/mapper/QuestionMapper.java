package com.exam.data.mapper;

import com.exam.data.dto.QuestionDTO;
import com.exam.data.enity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    @Mapping(target = "id", ignore = true)
    Question toEntity(QuestionDTO questionDTO);

    QuestionDTO toDTO(Question question);
}
