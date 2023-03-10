package com.sicredi.votesms.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String question;
}
