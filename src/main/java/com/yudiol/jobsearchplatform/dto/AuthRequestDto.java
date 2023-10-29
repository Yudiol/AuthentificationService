package com.yudiol.jobsearchplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_FORMAT_EMAIL;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_LENGTH_EMAIL;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_LENGTH_PASSWORD;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.NOT_EMPTY_VALUE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "AuthRequestDto принимает username и password")
public class AuthRequestDto {

    @NotBlank(message = "Поле 'Email'" + NOT_EMPTY_VALUE)
    @Size(max = 50, message = INCORRECT_LENGTH_EMAIL)
    @Email(message = INCORRECT_FORMAT_EMAIL)
    @Schema(description = "Почта", example = "Ivan@mail.com")
    private String username;

    @NotBlank(message = "Поле 'Password'" + NOT_EMPTY_VALUE)
    @Size(max = 50, message = INCORRECT_LENGTH_PASSWORD)
    @Schema(description = "Пароль", example = "wH&bkYvkV(aD")
    private String password;
}
