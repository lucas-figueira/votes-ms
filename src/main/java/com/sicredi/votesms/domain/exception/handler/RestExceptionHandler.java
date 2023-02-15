package com.sicredi.votesms.domain.exception.handler;

import com.sicredi.votesms.domain.exception.QuestionException;
import com.sicredi.votesms.domain.exception.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QuestionException.class)
    public final ResponseEntity<Object> handleQuestionException(final QuestionException exception) {
        logger.error(exception.getDescription());

        ResponseException exceptionResponse = ResponseException.builder()
                .code(exception.getErrorCodeEnum())
                .message(exception.getErrorMessage())
                .build();

        exceptionResponse.addDetailsItem(exception.getDescription());

        return ResponseEntity.status(exception.getHttpStatus()).body(exceptionResponse);
    }
}
