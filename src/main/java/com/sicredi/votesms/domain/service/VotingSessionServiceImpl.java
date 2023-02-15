package com.sicredi.votesms.domain.service;

import com.sicredi.votesms.domain.constants.ValidationConstraints;
import com.sicredi.votesms.domain.dto.VotingSessionDto;
import com.sicredi.votesms.domain.exception.QuestionException;
import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import com.sicredi.votesms.domain.mapper.VotingSessionMapper;
import com.sicredi.votesms.domain.model.VotingSession;
import com.sicredi.votesms.domain.repository.QuestionRepository;
import com.sicredi.votesms.domain.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class VotingSessionServiceImpl implements VotingSessionService{

    private final QuestionRepository questionRepository;
    private final VotingSessionRepository repository;
    private final VotingSessionMapper mapper;

    @Override
    @Transactional
    public Mono<VotingSessionDto> save(final Optional<Integer> minutesToExpire, final String questionDescription) {
        return questionRepository.findByDescription(questionDescription)
                .switchIfEmpty(
                        Mono.error(new QuestionException(ErrorCodeEnum.QUESTION_DOES_NOT_EXISTS,
                                ErrorMessageEnum.BAD_REQUEST,
                                ValidationConstraints.QUESTION_DOES_NOT_EXISTS)))
                .then(repository.findByQuestion(questionDescription)
                        .handle((exists, sink) -> {
                            if (exists == null) {
                                sink.next(exists);
                            } else {
                                sink.error(new QuestionException(ErrorCodeEnum.QUESTION_ALREADY_EXISTS,
                                        ErrorMessageEnum.CONFLICT,
                                        "Voting session already exists"));
                            }
                        }))
                .then(Mono.just(VotingSession.builder()
                                .startDate(LocalDateTime.now())
                                .endDate(LocalDateTime.now()
                                        .plusMinutes(minutesToExpire.isPresent() ? minutesToExpire.get() : 1))
                                .question(questionDescription)
                                .build())
                                .map(votingSession -> {
                                    repository.save(votingSession).subscribe();
                                    return mapper.toVotingSessionDto(votingSession);
                                })
                ).doOnSuccess(votingSession -> log.info("Voting session with question "
                        .concat("[")
                        .concat(votingSession.getQuestion())
                        .concat("]")
                        .concat(" successfully saved")));
    }

}
