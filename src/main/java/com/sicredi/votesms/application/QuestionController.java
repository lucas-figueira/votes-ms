package com.sicredi.votesms.application;

import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<QuestionDto> save(@RequestBody final QuestionDto questionDto) {

        return service.save(questionDto);
    }

}
