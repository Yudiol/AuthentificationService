package com.yudiol.jobsearchplatform.service;

import com.yudiol.jobsearchplatform.dto.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);

    Optional<RefreshToken> findByToken(String refreshToken);

    RefreshToken verifyExpiredToken(RefreshToken refreshToken);
}
