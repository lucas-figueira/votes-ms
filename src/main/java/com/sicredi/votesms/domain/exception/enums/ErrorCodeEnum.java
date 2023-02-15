package com.sicredi.votesms.domain.exception.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodeEnum {

    QUESTION_ALREADY_EXISTS("QUESTION_ALREADY_EXISTS"),
    QUESTION_DOES_NOT_EXISTS("QUESTION_DOES_NOT_EXISTS"),
    ASSOCIATE_ALREADY_EXISTS("ASSOCIATE_ALREADY_EXISTS"),
    INVALID_CPF("INVALID_CPF");

    private String value;

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

}
