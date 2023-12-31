package com.yudiol.jobsearchplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_FORMAT_EMAIL;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.NOT_EMPTY_VALUE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "AuthRequestLoginDto принимает username и password")
public class AuthRequestLoginDto {

    @NotBlank(message = "Поле 'Email'" + NOT_EMPTY_VALUE)
    @Email(message = INCORRECT_FORMAT_EMAIL)
    @Schema(description = "Почта", example = "Ivan@mail.com")
    private String username;

    @NotBlank(message = "Поле 'Password'" + NOT_EMPTY_VALUE)
    @Schema(description = "Пароль", example = "wH&bkYvkV(aD")
    private String password;
}
