package com.sicredi.votesms.domain.mapper;

import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.model.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question toQuestion (QuestionDto questionDto);
    QuestionDto toQuestionDto (Question question);
}
