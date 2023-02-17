package com.sicredi.votesms.domain.exception.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessageEnum {

    BAD_REQUEST("Validation failed", HttpStatus.BAD_REQUEST),
    PRECONDITION_FAILED("Precondition failed", HttpStatus.PRECONDITION_FAILED),
    CONFLICT("Entity already exists",HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorMessageEnum(final String message, final HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
