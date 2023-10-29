package com.yudiol.jobsearchplatform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.yudiol.jobsearchplatform.util.ValidationMessage.NOT_EMPTY_VALUE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestRefreshDto {
    @NotBlank(message = "Поле 'Токен'" + NOT_EMPTY_VALUE)
    private String refreshToken;
}
