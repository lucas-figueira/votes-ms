package com.sicredi.votesms.domain.repository;

import com.sicredi.votesms.domain.model.Question;
import reactor.core.publisher.Mono;

public interface QuestionRepository {

    Mono<Question> findByDescription(String description);

    Mono<Question> save(Question entity);

}
