package com.sicredi.votesms.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultDto {

    private Long yesVotes;
    private Long noVotes;
    private String winner;
}
