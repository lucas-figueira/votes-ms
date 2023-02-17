package com.sicredi.votesms.domain.repository;

import com.sicredi.votesms.domain.model.Question;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface QuestionRepository {

    Mono<Question> findByDescription(String description);

    Mono<Question> findByDescriptionAndVotesCpf(String description,String cpf);

    Mono<Question> save(Question entity);

}
