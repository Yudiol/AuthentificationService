package com.yudiol.jobsearchplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Schema(description = "AuthResponseRefreshDto отдаёт id, login и token")
public class AuthResponseRefreshDto {
    private Long id;
    private String email;
    private String accessToken;
}
