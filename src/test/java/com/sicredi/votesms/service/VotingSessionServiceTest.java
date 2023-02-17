package com.sicredi.votesms.service;

import com.sicredi.votesms.domain.dto.VotingSessionDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.mapper.VotingSessionMapper;
import com.sicredi.votesms.domain.model.Question;
import com.sicredi.votesms.domain.model.VotingSession;
import com.sicredi.votesms.domain.repository.QuestionRepository;
import com.sicredi.votesms.domain.repository.VotingSessionRepository;
import com.sicredi.votesms.domain.service.VotingSessionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VotingSessionServiceTest {
    @InjectMocks
    private VotingSessionServiceImpl service;

    @Mock
    private VotingSessionMapper mapper;

    @Mock
    private VotingSessionRepository repository;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void createVotingSessionShouldReturnCreated() {
        LocalDateTime startDate = getStartDate();
        LocalDateTime endDate = getEndDate();
        VotingSession votingSession = getVotingSession(startDate, endDate);

        VotingSessionDto votingSessionDto = getVotingSessionDto(startDate, endDate);

        Question question = getQuestion();

        when(questionRepository.findByDescription(anyString())).thenReturn(Mono.just(question));
        when(repository.findByQuestion(anyString())).thenReturn(Mono.empty());
        when(repository.save(any(VotingSession.class))).thenReturn(Mono.just(votingSession));
        when(mapper.toVotingSessionDto(any(VotingSession.class))).thenReturn(votingSessionDto);

        StepVerifier.create(service.save(Optional.of(3),"Estrutura"))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    public void createVotingSessionShouldReturnQuestionDoesNotExistsException() throws Exception {
        when(questionRepository.findByDescription(anyString())).thenReturn(Mono.empty());
        when(repository.findByQuestion(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(service.save(Optional.of(3), "Estrutura"))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException && customException.getDescription().equals("Question with description Estrutura does not exists");
                })
                .verify();
    }

    @Test
    public void createVotingSessionShouldReturnVotingSessionAlreadyExistsException() throws Exception {
        when(questionRepository.findByDescription(anyString())).thenReturn(Mono.just(getQuestion()));
        when(repository.findByQuestion(anyString())).thenReturn(Mono.just(getVotingSession(getStartDate(),getEndDate())));

        StepVerifier.create(service.save(Optional.of(3), "Estrutura"))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException && customException.getDescription().equals("Voting session with question Estrutura already exists");
                })
                .verify();
    }

    private Question getQuestion() {
        Question question = Question.builder().description("Estrutura").build();
        return question;
    }

    private LocalDateTime getEndDate() {
        var endDate = LocalDateTime.now().plusMinutes(3);
        return endDate;
    }

    private LocalDateTime getStartDate() {
        var startDate = LocalDateTime.now();
        return startDate;
    }

    private VotingSessionDto getVotingSessionDto(LocalDateTime startDate, LocalDateTime endDate) {
        VotingSessionDto votingSessionDto = VotingSessionDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .question("Estrutura")
                .build();
        return votingSessionDto;
    }

    private VotingSession getVotingSession(LocalDateTime startDate, LocalDateTime endDate) {
        VotingSession votingSession = VotingSession.builder().startDate(startDate)
                .endDate(endDate)
                .question("Estrutura")
                .build();
        return votingSession;
    }
}
