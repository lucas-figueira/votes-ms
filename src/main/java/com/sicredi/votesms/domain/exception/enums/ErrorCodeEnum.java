package com.sicredi.votesms.domain.exception.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorCodeEnum {

    QUESTION_ALREADY_EXISTS("QUESTION_ALREADY_EXISTS"),
    QUESTION_DOES_NOT_EXISTS("QUESTION_DOES_NOT_EXISTS");

    private String value;

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

}
