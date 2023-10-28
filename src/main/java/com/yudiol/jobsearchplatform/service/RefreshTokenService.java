package com.yudiol.jobsearchplatform.service;

import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.RefreshToken;
import com.yudiol.jobsearchplatform.dto.RefreshTokenRequestDto;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);

    Optional<RefreshToken> findByToken(String refreshToken);

    RefreshToken verifyExpiredToken(RefreshToken refreshToken);

    void deleteByEmail(String name);

    AuthResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
