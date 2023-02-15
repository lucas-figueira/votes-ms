package com.sicredi.votesms.domain.service;

import com.sicredi.votesms.domain.dto.VotingSessionDto;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface VotingSessionService {
    Mono<VotingSessionDto> save(Optional<Integer> minutesToExpire, String question);
}
