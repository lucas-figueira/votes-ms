package com.sicredi.votesms.service;

import com.sicredi.votesms.domain.dto.AssociateDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.mapper.AssociateMapper;
import com.sicredi.votesms.domain.model.Associate;
import com.sicredi.votesms.domain.repository.AssociateRepository;
import com.sicredi.votesms.domain.service.AssociateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {

    @InjectMocks
    private AssociateServiceImpl service;

    @Mock
    private AssociateMapper mapper;

    @Mock
    private AssociateRepository repository;


    @Test
    public void createAssociateShouldReturnCreated() {
        // given
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();

        when(mapper.toAssociate(any(AssociateDto.class))).thenReturn(associate);
        when(repository.findByCpf(anyString())).thenReturn(Mono.empty());
        when(repository.save(any(Associate.class))).thenReturn(Mono.just(associate));

        // when
        StepVerifier.create(service.save(AssociateDto.builder().cpf("28459195066").name("Neymar Junior").build()))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();

    }

    @Test
    public void createAssociateShouldReturnInvalidCpfException()throws Exception {
        Assertions.assertThrows(CustomException.class, () -> {
            StepVerifier.create(service.save(AssociateDto.builder().cpf("28419195066").name("Neymar Junior").build()))
                    .expectError(CustomException.class)
                    .verify();
        });

    }

    @Test
    public void createAssociateShouldReturnAssociateAlreadyExistsException() {
        Associate associate = Associate.builder().cpf("28459195066").name("Neymar Junior").build();
        when(repository.findByCpf(anyString())).thenReturn(Mono.just(associate));
        when(mapper.toAssociate(any(AssociateDto.class))).thenReturn(associate);
        when(repository.save(any(Associate.class))).thenReturn(Mono.just(associate));


        StepVerifier.create(service.save(AssociateDto.builder().cpf("28459195066").name("Neymar Junior").build()))
                .expectErrorMatches(error -> {
                    CustomException customException = (CustomException) error;
                    return error instanceof CustomException && customException.getDescription().equals("associate with cpf 28459195066 already been created");
                })
                .verify();
    }

}
