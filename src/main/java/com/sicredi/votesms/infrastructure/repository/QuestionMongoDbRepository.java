package com.sicredi.votesms.infrastructure.repository;

import com.sicredi.votesms.domain.model.Question;
import com.sicredi.votesms.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class QuestionMongoDbRepository implements QuestionRepository {

    private final QuestionReactiveSpringDataRepository questionReactiveSpringDataRepository;

    @Override
    public Mono<Question> findByDescription(String description) {
        return questionReactiveSpringDataRepository.findByDescription(description);
    }

    @Override
    public Mono<Question> save(Question entity) {
        return questionReactiveSpringDataRepository.save(entity);
    }
}
