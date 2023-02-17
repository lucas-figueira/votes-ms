package com.sicredi.votesms.domain.model;

import com.sicredi.votesms.domain.constants.ValidationConstraints;
import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
@Data
@AllArgsConstructor
@Document(collection = "votingsession")
public class VotingSession {

    @Id
    private String id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String question;

    public void validateVotingSessionIsStillOpen(String question) {
        if (LocalDateTime.now().isBefore(getEndDate())) {
            throw new CustomException(ErrorCodeEnum.VOTING_SESSION_IS_STILL_OPEN,
                    ErrorMessageEnum.PRECONDITION_FAILED,
                    StringUtils.replace(ValidationConstraints.VOTING_SESSION_IS_STILL_OPEN,
                            "{}", question));
        }
    }

    public void validateVotingSessionAlreadyClosed(QuestionDto questionDto) {
        if (LocalDateTime.now().isAfter(getEndDate().atZone(ZoneId.of("GMT-3")).toLocalDateTime())) {
            throw new CustomException(ErrorCodeEnum.VOTING_SESSION_ALREADY_CLOSED,
                    ErrorMessageEnum.PRECONDITION_FAILED,
                    StringUtils.replace(ValidationConstraints.VOTING_SESSION_ALREADY_CLOSED, "{}",
                            questionDto.getDescription()));
        }
    }
}
