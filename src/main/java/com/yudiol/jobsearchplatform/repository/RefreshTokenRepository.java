package com.yudiol.jobsearchplatform.repository;

import com.yudiol.jobsearchplatform.dto.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String refreshToken);

    void deleteByUserId(Long id);
}
