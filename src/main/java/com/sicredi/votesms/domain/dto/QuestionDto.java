package com.sicredi.votesms.domain.dto;


import com.sicredi.votesms.domain.model.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {

    private String description;

    private VoteDto vote;
}
