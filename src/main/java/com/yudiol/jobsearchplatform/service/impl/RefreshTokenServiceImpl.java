package com.yudiol.jobsearchplatform.service.impl;

import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.RefreshToken;
import com.yudiol.jobsearchplatform.dto.RefreshTokenRequestDto;
import com.yudiol.jobsearchplatform.exception.errors.InternalServerError;
import com.yudiol.jobsearchplatform.exception.errors.NotFoundException;
import com.yudiol.jobsearchplatform.model.User;
import com.yudiol.jobsearchplatform.repository.RefreshTokenRepository;
import com.yudiol.jobsearchplatform.repository.UserRepository;
import com.yudiol.jobsearchplatform.security.JwtTokenUtils;
import com.yudiol.jobsearchplatform.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new NotFoundException("пользователь", username));
        refreshTokenRepository.deleteByUserId(user.getId());
        return refreshToken(user);
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    public AuthResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenRequestDto.getRefreshToken()).orElseThrow(() ->
                new NotFoundException("Refresh token", refreshTokenRequestDto.getRefreshToken()));

        return Optional.of(refreshToken)
                .map(this::verifyExpiredToken)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = this.getJwtToken(user.getEmail());
                    return AuthResponseDto.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDto.getRefreshToken())
                            .build();
                }).orElseThrow(() -> new InternalServerError("Что-то пошло не так, внутренняя ошибка сервера"));
    }

    @Transactional
    public RefreshToken verifyExpiredToken(RefreshToken refreshToken) {
        if (refreshToken.getExpiredDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new NotFoundException("Refresh token", refreshToken.getToken());
        }
        return refreshToken;
    }

    @Transactional
    public void deleteByEmail(String name) {
        refreshTokenRepository.deleteByEmail(name);
    }

    @Transactional
    public RefreshToken refreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiredDate(Instant.now().plusSeconds(lifetimeRefreshToken))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public String getJwtToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtils.generateToken(userDetails);
    }
}
