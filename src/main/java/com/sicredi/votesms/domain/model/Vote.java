package com.sicredi.votesms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@AllArgsConstructor
@Document
public class Vote {

    private String cpf;
    private String vote;
}
