package com.sicredi.votesms.infrastructure.repository;

import com.sicredi.votesms.domain.model.Associate;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AssociateReactiveSpringDataRepository extends ReactiveMongoRepository<Associate, String> {

    Mono<Associate> findByCpf(String cpf);
}
