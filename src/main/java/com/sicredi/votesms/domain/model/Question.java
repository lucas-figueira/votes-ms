package com.sicredi.votesms.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sicredi.votesms.domain.constants.ValidationConstraints;
import com.sicredi.votesms.domain.dto.QuestionDto;
import com.sicredi.votesms.domain.dto.QuestionResultDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;
import reactor.core.publisher.SynchronousSink;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@Document
public class Question {

    @Id
    private String id;

    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Vote> votes;

    public void validateQuestionAlreadyExists(SynchronousSink<Object> sink) {
        if (this != null) {
            sink.error(new CustomException(ErrorCodeEnum.QUESTION_ALREADY_EXISTS,
                    ErrorMessageEnum.CONFLICT,
                    StringUtils.replace(ValidationConstraints.QUESTION_ALREADY_EXISTS, "{}", getDescription())
            ));
        }else{
            sink.next(this);
        }
    }

    public void validateAssociateAlreadyVoted(QuestionDto questionDto) {
        if (getVotes() != null) {
            getVotes().stream().filter(vote -> vote.getCpf()
                            .equals(questionDto.getVote().getCpf()))
                    .findFirst()
                    .ifPresent(vote -> {
                        throw new CustomException(ErrorCodeEnum.ASSOCIATE_ALREADY_VOTED,
                                ErrorMessageEnum.BAD_REQUEST,
                                StringUtils.replace(ValidationConstraints.ASSOCIATE_ALREADY_VOTED, "{}",
                                        questionDto.getVote().getCpf()));
                    });
        }
    }

    public QuestionResultDto verifyQuestionResults() {
        Long yesQuantity = getVotes().stream().filter(vote -> vote.getVote()
                        .equalsIgnoreCase("SIM"))
                .count();
        Long noQuantity = getVotes().stream().filter(vote -> vote.getVote()
                        .equalsIgnoreCase("NÃO"))
                .count();
        var questionResultDto = QuestionResultDto.builder()
                .yesVotes(yesQuantity)
                .noVotes(noQuantity)
                .build();

        if (yesQuantity > noQuantity) {
            questionResultDto.setWinner("SIM");
        } else {
            questionResultDto.setWinner("NÃO");
        }
        return questionResultDto;
    }
}
