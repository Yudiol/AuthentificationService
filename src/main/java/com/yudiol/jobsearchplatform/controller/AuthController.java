package com.yudiol.jobsearchplatform.controller;

import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.UserDto;
import com.yudiol.jobsearchplatform.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Регистрация пользователя")
    public AuthResponseDto register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }

    @GetMapping("/login")
    @Operation(summary = "Login")
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        return null;
    }
}
