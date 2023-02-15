package com.sicredi.votesms.domain.exception;

import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseException {

    private ErrorCodeEnum code = null;

    private String message = null;

    private List<String> details = null;

    public ResponseException addDetailsItem(String detailsItem) {
        if (this.details == null) {
            this.details = new ArrayList<>();
        }
        this.details.add(detailsItem);
        return this;
    }
}
