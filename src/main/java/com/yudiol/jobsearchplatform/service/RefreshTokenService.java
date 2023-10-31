package com.yudiol.jobsearchplatform.service;

import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.model.RefreshToken;
import com.yudiol.jobsearchplatform.dto.AuthRequestRefreshDto;
import com.yudiol.jobsearchplatform.model.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);

    Optional<RefreshToken> findByToken(String refreshToken);

    RefreshToken verifyExpiredToken(RefreshToken refreshToken);

    void deleteByEmail(String name);

    AuthResponseDto refreshToken(AuthRequestRefreshDto refreshTokenRequestDto);

    RefreshToken refreshToken(User user);
}
