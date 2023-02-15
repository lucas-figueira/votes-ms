package com.sicredi.votesms.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionDto {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String question;
}
