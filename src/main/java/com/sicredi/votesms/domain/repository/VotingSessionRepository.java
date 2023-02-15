package com.sicredi.votesms.domain.repository;

import com.sicredi.votesms.domain.model.Question;
import com.sicredi.votesms.domain.model.VotingSession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VotingSessionRepository extends ReactiveMongoRepository<VotingSession, String> {

    Mono<VotingSession> findByQuestion(String description);
}
