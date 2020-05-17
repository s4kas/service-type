package com.bmp.tests.servicetype.web;

import com.bmp.tests.servicetype.annotation.AutowiredByType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyRestController {

    @AutowiredByType
    private String aProperty;

    @GetMapping("/dummy")
    public String get() {
        return aProperty;
    }
}
