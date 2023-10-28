package com.yudiol.jobsearchplatform.service.impl;

import com.yudiol.jobsearchplatform.dto.RefreshToken;
import com.yudiol.jobsearchplatform.model.User;
import com.yudiol.jobsearchplatform.repository.RefreshTokenRepository;
import com.yudiol.jobsearchplatform.repository.UserRepository;
import com.yudiol.jobsearchplatform.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.lifetimeRefreshToken}")
    private Long lifetimeRefreshToken;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("UserDetailsService: пользователь с username '%s' не найден", username)));
        refreshTokenRepository.deleteByUserId(user.getId());
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiredDate(Instant.now().plusSeconds(lifetimeRefreshToken))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Transactional
    public RefreshToken verifyExpiredToken(RefreshToken refreshToken) {
        if (refreshToken.getExpiredDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            // throw exception
        }
        return refreshToken;
    }

    @Transactional
    public void deleteByEmail(String name) {
        refreshTokenRepository.deleteByEmail(name);
    }
}
