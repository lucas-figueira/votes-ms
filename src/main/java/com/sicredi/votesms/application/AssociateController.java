package com.sicredi.votesms.application;

import com.sicredi.votesms.domain.dto.AssociateDto;
import com.sicredi.votesms.domain.service.AssociateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/associates")
@Tag(name = "Associate", description = "Endpoints for associate")
public class AssociateController {

    private final AssociateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AssociateDto> save(@RequestBody final AssociateDto associateDto) {

        return service.save(associateDto);
    }

}
