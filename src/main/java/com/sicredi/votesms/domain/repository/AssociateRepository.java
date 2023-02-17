package com.sicredi.votesms.domain.repository;

import com.sicredi.votesms.domain.model.Associate;
import reactor.core.publisher.Mono;

public interface AssociateRepository {

    Mono<Associate> findByCpf(String cpf);

    Mono<Associate> save(Associate entity);
}
