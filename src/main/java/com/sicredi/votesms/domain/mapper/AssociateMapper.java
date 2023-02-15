package com.sicredi.votesms.domain.mapper;

import com.sicredi.votesms.domain.common.ValidateUtils;
import com.sicredi.votesms.domain.dto.AssociateDto;
import com.sicredi.votesms.domain.model.Associate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = { ValidateUtils.class })
public interface AssociateMapper {

    Associate toAssociate(AssociateDto associateDto);
}
