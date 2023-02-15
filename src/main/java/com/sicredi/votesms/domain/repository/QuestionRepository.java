package com.sicredi.votesms.domain.repository;

import com.sicredi.votesms.domain.model.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {

    Mono<Question> findByDescription(String description);
}
