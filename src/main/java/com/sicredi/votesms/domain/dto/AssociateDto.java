package com.sicredi.votesms.domain.dto;

import com.sicredi.votesms.domain.common.ValidateUtils;
import com.sicredi.votesms.domain.constants.ValidationConstraints;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociateDto {

    @Id
    private String cpf;

    private String name;

    public void validateCpf() {
        if (Boolean.FALSE.equals(ValidateUtils.isValidCpf(getCpf()))) {
            throw new CustomException(ErrorCodeEnum.INVALID_CPF,
                    ErrorMessageEnum.BAD_REQUEST,
                    StringUtils.replace(ValidationConstraints.INVALID_CPF, "{}", getCpf()));
        }
    }
}
