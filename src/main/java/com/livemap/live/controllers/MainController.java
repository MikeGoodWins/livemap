package com.livemap.live.controllers;

import com.livemap.live.dto.RequestModel;
import com.livemap.live.dto.ResponseModel;
import com.livemap.live.services.MainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping("/add")
    public ResponseModel add(@RequestBody RequestModel request) {
        return mainService.calculate(request);
    }
}
