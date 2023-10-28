package com.yudiol.jobsearchplatform.controller;

import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.RefreshToken;
import com.yudiol.jobsearchplatform.dto.RefreshTokenRequestDto;
import com.yudiol.jobsearchplatform.dto.UserDto;
import com.yudiol.jobsearchplatform.model.User;
import com.yudiol.jobsearchplatform.service.AuthService;
import com.yudiol.jobsearchplatform.service.RefreshTokenService;
import com.yudiol.jobsearchplatform.util.ErrorsValidationChecker;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@ResponseStatus(HttpStatus.OK)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Регистрация пользователя")
    public AuthResponseDto register(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return authService.register(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public AuthResponseDto createAuthToken(@RequestBody @Valid AuthRequestDto authRequestDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        AuthResponseDto authResponseDto = authService.createAuthToken(authRequestDto);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getUsername());
        authResponseDto.setRefreshToken(refreshToken.getToken());
        return authResponseDto;
    }

    @GetMapping("/logout")
    public String logout(Principal principal) {
        refreshTokenService.deleteByEmail(principal.getName());
        return null;
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновить access token")
    public AuthResponseDto refreshToken(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return refreshTokenService.findByToken(refreshTokenRequestDto.getRefreshToken())
                .map(refreshTokenService::verifyExpiredToken)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = authService.getJwtToken(user.getEmail());
                    return AuthResponseDto.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDto.getRefreshToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Something went wrong, access token has not been updated"));
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") Long id) {
        return authService.findById(id);
    }
}
