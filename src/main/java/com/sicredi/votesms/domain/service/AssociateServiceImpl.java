package com.sicredi.votesms.domain.service;

import com.sicredi.votesms.domain.common.ValidateUtils;
import com.sicredi.votesms.domain.constants.ValidationConstraints;
import com.sicredi.votesms.domain.dto.AssociateDto;
import com.sicredi.votesms.domain.exception.CustomException;
import com.sicredi.votesms.domain.exception.enums.ErrorCodeEnum;
import com.sicredi.votesms.domain.exception.enums.ErrorMessageEnum;
import com.sicredi.votesms.domain.mapper.AssociateMapper;
import com.sicredi.votesms.domain.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class AssociateServiceImpl implements AssociateService {

    private final AssociateRepository repository;
    private final AssociateMapper mapper;

    @Override
    public Mono<AssociateDto> save(AssociateDto associateDto) {
        String cpf = ValidateUtils.removeSpecialCharacter(associateDto.getCpf());
        associateDto.setCpf(cpf);

        associateDto.validateCpf();

        return validateAssociateAlreadyExists(associateDto)
                .then(repository.save(mapper.toAssociate(associateDto))
                .map(associate ->  associateDto)
                .doOnSuccess(question -> log.info("Associate "
                        .concat("[")
                        .concat(associateDto.getCpf())
                        .concat("]")
                        .concat(" successfully saved"))));
    }

    private Mono<Object> validateAssociateAlreadyExists(AssociateDto associateDto) {
        return repository.findByCpf(associateDto.getCpf())
                .handle((associate, sink) -> {
                    if (associate != null) {
                        sink.error(new CustomException(ErrorCodeEnum.ASSOCIATE_ALREADY_EXISTS,
                                ErrorMessageEnum.BAD_REQUEST,
                                StringUtils.replace(ValidationConstraints.ASSOCIATE_ALREADY_EXISTS, "{}", associateDto.getCpf())));
                    } else {
                         sink.next(associate);
                    }
                });
    }

}
