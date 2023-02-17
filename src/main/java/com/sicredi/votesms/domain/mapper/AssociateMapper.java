package com.sicredi.votesms.domain.mapper;

import com.sicredi.votesms.domain.dto.AssociateDto;
import com.sicredi.votesms.domain.model.Associate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssociateMapper {

    Associate toAssociate(AssociateDto associateDto);
}
