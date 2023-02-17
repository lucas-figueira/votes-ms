package com.sicredi.votesms.infrastructure.repository;

import com.sicredi.votesms.domain.model.Question;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface QuestionReactiveSpringDataRepository extends ReactiveMongoRepository<Question, String> {

    Mono<Question> findByDescription(String description);

    @Query("{'description' : ?0, 'votes.cpf' : ?1}")
    Mono<Question> findByDescriptionAndVotesCpf(String description,String cpf);

}
