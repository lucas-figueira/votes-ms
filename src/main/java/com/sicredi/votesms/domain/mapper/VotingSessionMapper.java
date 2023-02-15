package com.sicredi.votesms.domain.mapper;

import com.sicredi.votesms.domain.dto.VotingSessionDto;
import com.sicredi.votesms.domain.model.VotingSession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotingSessionMapper {

    VotingSessionDto toVotingSessionDto(VotingSession votingSession);
}
