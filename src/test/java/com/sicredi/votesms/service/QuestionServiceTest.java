package com.sicredi.votesms.service;

import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.dto.VoteDto;
import com.sicredi.votesms.domain.dto.VotingSessionDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.mapper.QuestionMapper;
import com.sicredi.votesms.domain.model.Associate;
import com.sicredi.votesms.domain.model.Question;
import com.sicredi.votesms.domain.model.Vote;
import com.sicredi.votesms.domain.model.VotingSession;
import com.sicredi.votesms.domain.repository.AssociateRepository;
import com.sicredi.votesms.domain.repository.QuestionRepository;
import com.sicredi.votesms.domain.repository.VotingSessionRepository;
import com.sicredi.votesms.domain.service.QuestionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @InjectMocks
    private QuestionServiceImpl service;

    @Mock
    private QuestionMapper mapper;

    @Mock
    private QuestionRepository repository;

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private AssociateRepository associateRepository;

    @Test
    public void createQuestionShouldReturnCreated() {
        when(repository.findByDescription(anyString())).thenReturn(Mono.empty());
        when(mapper.toQuestion(any(QuestionDto.class))).thenReturn(getQuestion());
        when(repository.save(any(Question.class))).thenReturn(Mono.just(getQuestion()));

        StepVerifier.create(service.save(getQuestionDto()))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    public void createVotingSessionShouldReturnQuestionDoesNotExistsException() throws Exception {
        when(repository.findByDescription(anyString())).thenReturn(Mono.just(getQuestion()));
        when(mapper.toQuestion(any(QuestionDto.class))).thenReturn(getQuestion());
        when(repository.save(any(Question.class))).thenReturn(Mono.just(getQuestion()));

        StepVerifier.create(service.save(getQuestionDto()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException &&
                            customException.getDescription().equals("Question with description Estrutura already been created");
                })
                .verify();
    }

    @Test
    public void updateQuestionWithVotesShouldReturnCreated() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(associateRepository.findByCpf(anyString())).thenReturn(Mono.just(associate));
        when(repository.findByDescription(anyString())).thenReturn(Mono.just(getQuestion()));
        when(votingSessionRepository.findByQuestion(anyString())).thenReturn(Mono.just(getVotingSession(getStartDate(),getEndDate())));
        when(repository.save(any(Question.class))).thenReturn(Mono.just(getQuestionWithVote()));

        StepVerifier.create(service.updateQuestionWithVotes(getQuestionDtoWithVote()))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();

    }

    @Test
    public void updateQuestionWithVotesShouldReturnQuestionDoesNotExistsException() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(associateRepository.findByCpf(anyString())).thenReturn(Mono.just(associate));
        when(repository.findByDescription(anyString())).thenReturn(Mono.empty());
        when(votingSessionRepository.findByQuestion(anyString())).thenReturn(Mono.just(getVotingSession(getStartDate(),getEndDate())));

        StepVerifier.create(service.updateQuestionWithVotes(getQuestionDtoWithVote()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException &&
                            customException.getDescription().equals("Question with description Estrutura does not exists");
                })
                .verify();

    }

    @Test
    public void updateQuestionWithVotesShouldReturnAssociateDoesNotExistsException() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(associateRepository.findByCpf(anyString())).thenReturn(Mono.empty());
        when(repository.findByDescription(anyString())).thenReturn(Mono.just(getQuestion()));
        when(votingSessionRepository.findByQuestion(anyString())).thenReturn(Mono.just(getVotingSession(getStartDate(),getEndDate())));

        StepVerifier.create(service.updateQuestionWithVotes(getQuestionDtoWithVote()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException &&
                            customException.getDescription().equals("associate with cpf 28459195066 does not exists");
                })
                .verify();
    }

    @Test
    public void updateQuestionWithVotesShouldReturnAssociateDoesNotExists() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(associateRepository.findByCpf(anyString())).thenReturn(Mono.empty());
        when(repository.findByDescription(anyString())).thenReturn(Mono.just(getQuestion()));
        when(votingSessionRepository.findByQuestion(anyString())).thenReturn(Mono.just(getVotingSession(getStartDate(),getEndDate())));

        StepVerifier.create(service.updateQuestionWithVotes(getQuestionDtoWithVote()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException &&
                            customException.getDescription().equals("associate with cpf 28459195066 does not exists");
                })
                .verify();
    }

    @Test
    public void updateQuestionWithVotesShouldReturnAssociateAlreadyVotedException() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(associateRepository.findByCpf(anyString())).thenReturn(Mono.just(associate));
        when(repository.findByDescription(anyString())).thenReturn(Mono.just(getQuestionWithVote()));
        when(votingSessionRepository.findByQuestion(anyString())).thenReturn(Mono.just(getVotingSession(getStartDate(),getEndDate())));

        StepVerifier.create(service.updateQuestionWithVotes(getQuestionDtoWithVote()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException &&
                            customException.getDescription().equals("associate with cpf 28459195066 already voted for this question");
                })
                .verify();
    }

    @Test
    public void updateQuestionWithVotesShouldReturnVotingSessionWithQuestionDoesNotExistsException() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(associateRepository.findByCpf(anyString())).thenReturn(Mono.just(associate));
        when(repository.findByDescription(anyString())).thenReturn(Mono.just(getQuestion()));
        when(votingSessionRepository.findByQuestion(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(service.updateQuestionWithVotes(getQuestionDtoWithVote()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException &&
                            customException.getDescription().equals("Voting session with question Estrutura does not exists");
                })
                .verify();
    }

    @Test
    public void updateQuestionWithVotesShouldReturnVotingSessionAlreadyClosedException() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(associateRepository.findByCpf(anyString())).thenReturn(Mono.just(associate));
        when(repository.findByDescription(anyString())).thenReturn(Mono.just(getQuestion()));
        VotingSession votingSession = getVotingSession(getStartDate().minusMinutes(-3),getEndDate().minusMinutes(3));
        when(votingSessionRepository.findByQuestion(anyString())).thenReturn(Mono.just(votingSession));

        StepVerifier.create(service.updateQuestionWithVotes(getQuestionDtoWithVote()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException &&
                            customException.getDescription().equals("Voting session with question Estrutura already closed");
                })
                .verify();
    }

    private Question getQuestionWithVote() {
        Question question = Question.builder()
                .description("Estrutura")
                .votes(Arrays.asList(Vote.builder()
                                    .cpf("28459195066")
                                    .vote("SIM")
                                    .build()))
                .build();
        return question;
    }

    private Question getQuestion() {
        Question question = Question.builder()
                .description("Estrutura")
                .build();
        return question;
    }

    private QuestionDto getQuestionDto() {
        QuestionDto questionDto = QuestionDto.builder().description("Estrutura").build();
        return questionDto;
    }

    private QuestionDto getQuestionDtoWithVote() {
        QuestionDto questionDto = QuestionDto.builder()
                .description("Estrutura")
                .vote(VoteDto.builder()
                    .cpf("28459195066")
                    .vote("SIM")
                    .build())
                .build();
        return questionDto;
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
