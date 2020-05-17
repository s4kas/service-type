package com.bmp.tests.servicetype.web;

import com.bmp.tests.servicetype.annotation.AutowiredByType;
import com.bmp.tests.servicetype.service.PetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetRestController {

    @AutowiredByType
    private PetService petService;

    @Value("${test:123}")
    private String test;

    @GetMapping
    public String walk(String petName) {
        return petService.walk(petName);
    }
}
