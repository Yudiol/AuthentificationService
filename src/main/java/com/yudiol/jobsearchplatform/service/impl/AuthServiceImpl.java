package com.yudiol.jobsearchplatform.service.impl;//package com.yudiol.jobsearchplatform.service.impl;

import com.yudiol.jobsearchplatform.dto.AuthRequestRegDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseActivateDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.exception.errors.BadRequestError;
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
    private final EmailServiceImpl emailService;

    private final String RESPONSE_ACTIVATE = "На почту которую вы указали отправили email";

    @Transactional
    public AuthResponseDto createAuthToken(String username, String password) {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new NotFoundException("Пользователь", username));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = getJwtToken(username);
        return new AuthResponseDto(user.getId(), user.getEmail(), token, UUID.randomUUID().toString());
    }

    @Transactional
    public AuthResponseActivateDto register(AuthRequestRegDto userDto) {
        String activeCode = UUID.randomUUID().toString();

        sendEmail(userDto.getEmail(), activeCode);

        User user = userMapper.toUser(userDto);
        user.setActiveCode(activeCode);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new AuthResponseActivateDto(RESPONSE_ACTIVATE);
    }

    @Transactional
    public AuthResponseActivateDto activationRequest(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("Пользователь", email));
        String activeCode = user.getActiveCode();

        if (activeCode == null) {
            activeCode = UUID.randomUUID().toString();
            user.setActive(false);
            user.setActiveCode(activeCode);
            userRepository.save(user);
        }

        sendEmail(email, user.getActiveCode());

        return new AuthResponseActivateDto(RESPONSE_ACTIVATE);
    }

    @Transactional
    public AuthResponseDto activate(String activeCode) {
        User user = userRepository.findByActiveCode(activeCode).orElseThrow(() ->
                new NotFoundException("User", activeCode));
        user.setActive(true);
        user.setActiveCode(null);
        userRepository.save(user);
        return new AuthResponseDto(user.getId(), user.getEmail(), getJwtToken(user.getEmail()), refreshTokenService.refreshToken(user).getToken());
    }


    public String getJwtToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtils.generateToken(userDetails);
    }

    @Transactional
    public AuthResponseActivateDto reset(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("User", email));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return new AuthResponseActivateDto("Пароль был успешно изменён");
    }

    public User findById(Long id, String email) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Пользователь", String.valueOf(id)));
        if (!user.getEmail().equals(email)) {
            throw new BadRequestError("Вы не имеете доступ к id = " + user.getId());
        }
        return user;
    }

    private void sendEmail(String email, String activeCode) {
        emailService.sendSimpleEmail(email, "Подтверждение E-mail", String.format(
                "Привет, %s! \n" +
                        "Пожалуйста пройдите по ссылке что бы подтвердить свою учетную запись на JobSearchPlatform : http://localhost:8083/auth/activate/%s",
                email,
                activeCode));
    }

}
