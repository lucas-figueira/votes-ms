package com.sicredi.votesms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
}
