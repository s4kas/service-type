package com.bmp.tests.servicetype.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface ServiceByType {

    ServiceType value();
}
