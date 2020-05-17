package com.bmp.tests.servicetype.service;

import com.bmp.tests.servicetype.annotation.ServiceByType;
import org.springframework.stereotype.Service;

import static com.bmp.tests.servicetype.annotation.ServiceType.DOG;

@ServiceByType(DOG)
public class DogService implements PetService {
    @Override
    public String walk(String petName) {
        return String.format("Walking a dog named %s", petName);
    }
}
