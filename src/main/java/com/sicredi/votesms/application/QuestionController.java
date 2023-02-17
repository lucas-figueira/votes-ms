package com.sicredi.votesms.application;

import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.dto.QuestionResultDto;
import com.sicredi.votesms.domain.dto.VoteDto;
import com.sicredi.votesms.domain.model.Question;
import com.sicredi.votesms.domain.service.QuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
@Tag(name = "Question", description = "Endpoints for Question")
public class QuestionController {

    private final QuestionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<QuestionDto> save(@RequestBody final QuestionDto questionDto) {

        return service.save(questionDto);
    }

    @PutMapping("/votes")
    public Mono<Question> updateQuestionWithVotes(@RequestBody final QuestionDto questionDto) {

        return service.updateQuestionWithVotes(questionDto);
    }

    @GetMapping("/results")
    public Mono<QuestionResultDto> calculateQuestionResult(final @RequestParam("question") String question) {

        return service.calculateQuestionResult(question);
    }

}
