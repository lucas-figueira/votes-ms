package com.sicredi.votesms.domain.repository;

import com.sicredi.votesms.domain.model.VotingSession;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface VotingSessionRepository {

    Mono<VotingSession> findByQuestion(String description);

    Mono<VotingSession> save(VotingSession entity);
}
