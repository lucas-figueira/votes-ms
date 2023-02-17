package com.sicredi.votesms.infrastructure.repository;

import com.sicredi.votesms.domain.model.Associate;
import com.sicredi.votesms.domain.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AssociateMongoDbRepository implements AssociateRepository {

    private final AssociateReactiveSpringDataRepository associateReactiveSpringDataRepository;

    @Override
    public Mono<Associate> findByCpf(String cpf) {
        return associateReactiveSpringDataRepository.findByCpf(cpf);
    }

    @Override
    public Mono<Associate> save(Associate entity) {
        return associateReactiveSpringDataRepository.save(entity);
    }
}
