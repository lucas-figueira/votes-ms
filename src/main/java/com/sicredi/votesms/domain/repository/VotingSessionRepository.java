package com.sicredi.votesms.domain.repository;

import com.sicredi.votesms.domain.model.Associate;
import com.sicredi.votesms.domain.model.Question;
import com.sicredi.votesms.domain.model.VotingSession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Component
public interface VotingSessionRepository {

    Mono<VotingSession> findByQuestion(String description);

    Mono<VotingSession> save(VotingSession entity);
}
