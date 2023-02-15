package com.sicredi.votesms.domain.exception;

import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;

public class CustomException extends BaseException {

    public CustomException(
            final ErrorCodeEnum errorCodeEnum,
            final ErrorMessageEnum errorMessageEnum,
            final String description) {
        super(errorCodeEnum, errorMessageEnum, description);
    }
}
