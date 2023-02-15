package com.sicredi.votesms.domain.exception;

import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class BaseException extends RuntimeException {

    private ErrorCodeEnum errorCodeEnum;
    private ErrorMessageEnum errorMessageEnum;
    private String description;

    public String getErrorMessage() {
        return errorMessageEnum.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return errorMessageEnum.getHttpStatus();
    }
}
