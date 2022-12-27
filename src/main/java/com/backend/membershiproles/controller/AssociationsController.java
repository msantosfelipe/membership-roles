package com.backend.membershiproles.controller;

import com.backend.membershiproles.model.dto.AssociationDto;
import com.backend.membershiproles.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/associations")
public class AssociationsController {

    @Autowired
    private AssociationService associationService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody AssociationDto associationDto){
        associationService.createAssociation(associationDto);
    }
}
