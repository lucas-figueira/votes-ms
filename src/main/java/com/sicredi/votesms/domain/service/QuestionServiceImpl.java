package com.sicredi.votesms.domain.service;

import com.sicredi.votesms.domain.constants.ValidationConstraints;
import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import com.sicredi.votesms.domain.mapper.QuestionMapper;
import com.sicredi.votesms.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper mapper;
    private final QuestionRepository repository;

    @Override
    @Transactional
    public Mono<QuestionDto> save(final QuestionDto questionDto) {
         return repository.findByDescription(questionDto.getDescription())
                 .handle((question, sink) ->{
                     if (question != null) {
                         sink.error(new CustomException(ErrorCodeEnum.QUESTION_ALREADY_EXISTS,
                                 ErrorMessageEnum.CONFLICT,
                                 StringUtils.replace(ValidationConstraints.QUESTION_ALREADY_EXISTS, "{}", question.getDescription())
                         ));
                     }else{
                         sink.next(question);
                     }
                 }).then(repository.save(mapper.toQuestion(questionDto)))
                 .map(a -> questionDto)
                 .doOnSuccess(question -> log.info("Question "
                         .concat("[")
                         .concat(question.getDescription())
                         .concat("]")
                         .concat(" successfully saved")));
    }
}
