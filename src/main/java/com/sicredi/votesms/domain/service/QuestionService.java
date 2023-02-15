package com.sicredi.votesms.domain.service;

import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.model.Question;
import reactor.core.publisher.Mono;

public interface QuestionService {

    Mono<QuestionDto> save(QuestionDto questionDto);
}
