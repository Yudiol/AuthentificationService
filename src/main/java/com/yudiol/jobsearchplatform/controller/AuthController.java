package com.yudiol.jobsearchplatform.controller;

import com.yudiol.jobsearchplatform.dto.AuthRequestActivateDto;
import com.yudiol.jobsearchplatform.dto.AuthRequestLoginDto;
import com.yudiol.jobsearchplatform.dto.AuthRequestPasswordDto;
import com.yudiol.jobsearchplatform.dto.AuthRequestRefreshDto;
import com.yudiol.jobsearchplatform.dto.AuthRequestRegDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseActivateDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseRefreshDto;
import com.yudiol.jobsearchplatform.exception.errors.BadRequestError;
import com.yudiol.jobsearchplatform.model.RefreshToken;
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
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Регистрация пользователя")
    public AuthResponseActivateDto register(@RequestBody @Valid AuthRequestRegDto userDto, BindingResult bindingResult) {
        comparePassword(userDto.getPassword(), userDto.getSecondPassword());
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return authService.register(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public AuthResponseDto createAuthToken(@RequestBody @Valid AuthRequestLoginDto authRequestDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        AuthResponseDto authResponseDto = authService.createAuthToken(authRequestDto.getUsername(), authRequestDto.getPassword());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getUsername());
        authResponseDto.setRefreshToken(refreshToken.getToken());
        return authResponseDto;
    }

    @GetMapping("/logout")
    @Operation(summary = "Logout")
    public String logout(Principal principal) {
        refreshTokenService.deleteByEmail(principal.getName());
        return null;
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновить access token")
    public AuthResponseRefreshDto refreshToken(@RequestBody @Valid AuthRequestRefreshDto refreshTokenRequestDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        AuthResponseDto refresh = refreshTokenService.refreshToken(refreshTokenRequestDto);
        return new AuthResponseRefreshDto(refresh.getId(), refresh.getEmail(), refresh.getAccessToken());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя")
    public User findOne(@PathVariable("id") Long id, Principal principal) {
        return authService.findById(id, principal.getName());
    }

    @GetMapping("/activate/{activeCode}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Подтверждение учётной записи")
    public AuthResponseDto activate(@PathVariable String activeCode) {
        return authService.activate(activeCode);
    }

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "запрос на подтверждение учётной записи")
    public AuthResponseActivateDto activationRequest(@RequestBody @Valid AuthRequestActivateDto authRequestActivateDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return authService.activationRequest(authRequestActivateDto.getEmail());
    }

    @PostMapping("/reset")
    @Operation(summary = "Сброс пароля")
    public AuthResponseActivateDto reset(@RequestBody @Valid AuthRequestPasswordDto userDto, Principal principal, BindingResult bindingResult) {
        comparePassword(userDto.getPassword(), userDto.getSecondPassword());
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return authService.reset(principal.getName(), userDto.getPassword());
    }

    private void comparePassword(String password1, String password2) {
        if (!password1.equals(password2)) {
            throw new BadRequestError("Пароли не совпадают");
        }
    }

}
