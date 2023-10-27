package com.yudiol.jobsearchplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_FORMAT_EMAIL;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_LENGTH_EMAIL;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_LENGTH_NAME;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.INCORRECT_SYMBOL;
import static com.yudiol.jobsearchplatform.util.ValidationMessage.NOT_EMPTY_VALUE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Schema(description = "UserDto принимает name, surname, login и password")
public class UserDto {
    @Pattern(message = "Поле 'Имя'" + INCORRECT_SYMBOL, regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @NotBlank(message = "Поле 'Имя'" + NOT_EMPTY_VALUE)
    @Size(max = 250, message = INCORRECT_LENGTH_NAME)
    @Schema(description = "Имя", example = "Иван")
    private String name;

    @Pattern(message = "Поле 'Фамилия'" + INCORRECT_SYMBOL, regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @NotBlank(message = "Поле 'Фамилия'" + NOT_EMPTY_VALUE)
    @Size(max = 250, message = INCORRECT_LENGTH_NAME)
    @Schema(description = "Фамилия", example = "Иванов")
    private String surname;

    @NotBlank(message = "Поле 'Email'" + NOT_EMPTY_VALUE)
    @Size(max = 250, message = INCORRECT_LENGTH_EMAIL)
    @Email(message = INCORRECT_FORMAT_EMAIL)
    @Schema(description = "Почта", example = "Ivan@mail.com")
    private String email;

    @NotBlank(message = "Поле 'Email'" + NOT_EMPTY_VALUE)
    @Size(max = 250, message = INCORRECT_LENGTH_EMAIL)
    @Schema(description = "Пароль", example = "wH&bkYvkV(aD")
    private String password;
}
