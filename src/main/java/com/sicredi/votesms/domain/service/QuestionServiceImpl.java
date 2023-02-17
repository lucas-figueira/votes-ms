package com.sicredi.votesms.domain.service;

import com.sicredi.votesms.domain.common.ValidateUtils;
import com.sicredi.votesms.domain.constants.ValidationConstraints;
import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.dto.QuestionResultDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import com.sicredi.votesms.domain.mapper.QuestionMapper;
import com.sicredi.votesms.domain.model.Associate;
import com.sicredi.votesms.domain.model.Question;
import com.sicredi.votesms.domain.model.Vote;
import com.sicredi.votesms.domain.model.VotingSession;
import com.sicredi.votesms.domain.repository.AssociateRepository;
import com.sicredi.votesms.domain.repository.QuestionRepository;
import com.sicredi.votesms.domain.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper mapper;
    private final QuestionRepository repository;
    private final VotingSessionRepository votingSessionRepository;
    private final AssociateRepository associateRepository;

    @Override
    @Transactional
    public Mono<QuestionDto> save(final QuestionDto questionDto) {
         return repository.findByDescription(questionDto.getDescription())
                 .handle(Question::validateQuestionAlreadyExists).then(repository.save(mapper.toQuestion(questionDto)))
                 .map(a -> questionDto)
                 .doOnSuccess(question -> log.info("Question "
                         .concat("[")
                         .concat(question.getDescription())
                         .concat("]")
                         .concat(" successfully saved")));
    }

    @Override
    @Transactional
    public Mono<Question> updateQuestionWithVotes(QuestionDto questionDto) {
        AtomicReference<Question> questionReturned = new AtomicReference<>();
        String cpf = ValidateUtils.removeSpecialCharacter(questionDto.getVote().getCpf());
        questionDto.getVote().setCpf(cpf);

        return validateAssociateDoesNotExists(questionDto)
                .then(validateQuestionDoesNotExists(questionDto.getDescription())
                    .map(question -> {
                        question.validateAssociateAlreadyVoted(questionDto);
                        questionReturned.set(question);
                        return question;
                    }).then(validateVotingSessionDoesNotExists(questionDto.getDescription())
                        .map(votingSession -> {
                            votingSession.validateVotingSessionAlreadyClosed(questionDto);
                            return votingSession;
                        })).flatMap(question ->saveQuestionWithVote(questionDto, questionReturned)))
                .doOnSuccess(votingSession -> saveQuestionWithVoteSucessLog(questionDto, votingSession));
    }

    @Override
    public Mono<QuestionResultDto> calculateQuestionResult(final String question) {
        return validateVotingSessionDoesNotExists(question)
                .doOnSuccess(votingSession -> votingSession.validateVotingSessionIsStillOpen(question)).then(validateQuestionDoesNotExists(question)
                        .map(Question::verifyQuestionResults));
    }

    private void saveQuestionWithVoteSucessLog(QuestionDto questionDto, Question votingSession) {
        log.info("Vote on question "
                .concat("[")
                .concat(votingSession.getDescription())
                .concat("]")
                .concat(" of CPF ")
                .concat("[")
                .concat(questionDto.getVote().getCpf())
                .concat("]")
                .concat(" successfully saved"));
    }

    private Mono<Question> saveQuestionWithVote(QuestionDto questionDto, AtomicReference<Question> questionReturned) {
        if (questionReturned.get().getVotes() == null) {
            questionReturned.get().setVotes(new ArrayList<>());
        }
        questionReturned.get().getVotes().add(Vote.builder()
                .vote(questionDto.getVote().getVote())
                .cpf(questionDto.getVote().getCpf())
                .build());
        return repository.save(questionReturned.get());
    }

    private Mono<VotingSession> validateVotingSessionDoesNotExists(String description) {
        return votingSessionRepository.findByQuestion(description)
                .switchIfEmpty(Mono.error(new CustomException(ErrorCodeEnum.VOTING_SESSION_WITH_QUESTION_NOT_EXISTS,
                        ErrorMessageEnum.BAD_REQUEST,
                        StringUtils.replace(ValidationConstraints.VOTING_SESSION_WITH_QUESTION_DOES_NOT_EXISTS,
                                "{}", description))));
    }

    private Mono<Question> validateQuestionDoesNotExists(String description) {
        return repository.findByDescription(description)
                .switchIfEmpty(
                        Mono.error(new CustomException(ErrorCodeEnum.QUESTION_DOES_NOT_EXISTS,
                                ErrorMessageEnum.BAD_REQUEST,
                                StringUtils.replace(ValidationConstraints.QUESTION_DOES_NOT_EXISTS, "{}",
                                        description))));
    }

    private Mono<Associate> validateAssociateDoesNotExists(QuestionDto questionDto) {
        return associateRepository.findByCpf(questionDto.getVote().getCpf())
                .switchIfEmpty(Mono.error(new CustomException(ErrorCodeEnum.ASSOCIATE_DOES_NOT_EXISTS,
                        ErrorMessageEnum.BAD_REQUEST,
                        StringUtils.replace(ValidationConstraints.ASSOCIATE_DOES_NOT_EXISTS, "{}",
                                questionDto.getVote().getCpf()))));
    }

}
