package com.yudiol.jobsearchplatform.service.impl;//package com.yudiol.jobsearchplatform.service.impl;

import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.UserDto;
import com.yudiol.jobsearchplatform.exception.errors.NotFoundException;
import com.yudiol.jobsearchplatform.mapper.UserMapper;
import com.yudiol.jobsearchplatform.model.User;
import com.yudiol.jobsearchplatform.repository.UserRepository;
import com.yudiol.jobsearchplatform.security.JwtTokenUtils;
import com.yudiol.jobsearchplatform.service.AuthService;
import com.yudiol.jobsearchplatform.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public AuthResponseDto createAuthToken(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = getJwtToken(username);
        User user = userRepository.findByEmail(username).orElseThrow();
        return new AuthResponseDto(user.getId(), user.getEmail(), token, UUID.randomUUID().toString());
    }

    @Transactional
    public AuthResponseDto register(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new AuthResponseDto(user.getId(), user.getEmail(), getJwtToken(userDto.getEmail()), refreshTokenService.refreshToken(user).getToken());
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Пользователь", String.valueOf(id)));
    }

    public String getJwtToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtils.generateToken(userDetails);
    }
}
