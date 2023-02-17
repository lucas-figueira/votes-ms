package com.sicredi.votesms.domain.exception.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCodeEnum {

    QUESTION_ALREADY_EXISTS("QUESTION_ALREADY_EXISTS"),
    QUESTION_DOES_NOT_EXISTS("QUESTION_DOES_NOT_EXISTS"),
    ASSOCIATE_DOES_NOT_EXISTS("ASSOCIATE_DOES_NOT_EXISTS"),
    ASSOCIATE_ALREADY_EXISTS("ASSOCIATE_ALREADY_EXISTS"),
    ASSOCIATE_ALREADY_VOTED("ASSOCIATE_ALREADY_VOTED"),
    VOTING_SESSION_ALREADY_CLOSED("VOTING_SESSION_ALREADY_CLOSED"),
    VOTING_SESSION_WITH_QUESTION_NOT_EXISTS("VOTING_SESSION_WITH_QUESTION_NOT_EXISTS"),
    VOTING_SESSION_IS_STILL_OPEN("VOTING_SESSION_IS_STILL_OPEN"),
    INVALID_CPF("INVALID_CPF");

    private String value;

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

}
