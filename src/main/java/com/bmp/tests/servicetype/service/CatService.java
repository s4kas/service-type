package com.bmp.tests.servicetype.service;

import com.bmp.tests.servicetype.annotation.ServiceByType;

import static com.bmp.tests.servicetype.annotation.ServiceType.CAT;

@ServiceByType(CAT)
public class CatService implements PetService {
    @Override
    public String walk(String petName) {
        return String.format("Walking a cat named %s", petName);
    }
}
