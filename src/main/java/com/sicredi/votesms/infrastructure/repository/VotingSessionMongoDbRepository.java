package com.sicredi.votesms.infrastructure.repository;

import com.sicredi.votesms.domain.model.VotingSession;
import com.sicredi.votesms.domain.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class VotingSessionMongoDbRepository implements VotingSessionRepository {
    private final VotingSessionReactiveSpringDataRepository votingSessionReactiveSpringDataRepository;

    @Override
    public Mono<VotingSession> findByQuestion(String description) {
        return votingSessionReactiveSpringDataRepository.findByQuestion(description);
    }

    @Override
    public Mono<VotingSession> save(VotingSession entity) {
        return votingSessionReactiveSpringDataRepository.save(entity);
    }
}
