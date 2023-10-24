package com.yudiol.jobsearchplatform.controller;

import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@ResponseStatus(HttpStatus.OK)
public class AuthController {

    @GetMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponseDto create(AuthRequestDto authRequestDto) {
        return null;
    }

    @GetMapping("/login")
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        return null;
    }
}
