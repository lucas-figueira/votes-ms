package com.sicredi.votesms.application;

import com.sicredi.votesms.domain.dto.VotingSessionDto;
import com.sicredi.votesms.domain.service.VotingSessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/voting-sessions")
@Tag(name = "Voting session", description = "Endpoints for voting session")
public class VotingSessionController {

    private final VotingSessionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<VotingSessionDto> save(final @RequestParam("question") String question,
                                       final @RequestParam("minutesToExpire") Optional<Integer> minutesToExpire) {
        return service.save(minutesToExpire, question);
    }
}
