package com.yudiol.jobsearchplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_LENGTH_PASSWORD;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.NOT_EMPTY_VALUE;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.NOT_SPACES_VALUE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Schema(description = "AuthRequestPasswordDto принимает password и secondPassword")
public class AuthRequestPasswordDto {

    @Pattern(message = "Поле 'Пароль' должно иметь хотя бы одну заглавную букву", regexp = ".*[A-ZА-Я]+.*")
    @Pattern(message = "Поле 'Пароль' должно иметь хотя бы одну прописную букву", regexp = ".*[a-zа-я]+.*")
    @Pattern(message = "Поле 'Пароль' должно иметь хотя бы один спецсимвол", regexp = ".*[\\W]+.*")
    @Pattern(message = "Поле 'Пароль'" + NOT_SPACES_VALUE, regexp = "\\S+")
    @NotBlank(message = "Поле 'Пароль'" + NOT_EMPTY_VALUE)
    @Size(min = 8, max = 50, message = INCORRECT_LENGTH_PASSWORD)
    @Schema(description = "Пароль", example = "wH&bkYvkV(aD")
    private String password;

    @Schema(description = "Пароль", example = "wH&bkYvkV(aD")
    private String secondPassword;
}
