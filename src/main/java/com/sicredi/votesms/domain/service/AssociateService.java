package com.sicredi.votesms.domain.service;

import com.sicredi.votesms.domain.dto.AssociateDto;
import com.sicredi.votesms.domain.model.Associate;
import reactor.core.publisher.Mono;

public interface AssociateService {
    Mono<AssociateDto> save(AssociateDto associateDto);
}
