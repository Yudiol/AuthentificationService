package com.yudiol.jobsearchplatform.service.impl;//package com.yudiol.jobsearchplatform.service.impl;
//
//import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
//import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
//import com.yudiol.jobsearchplatform.dto.UserDto;
//import com.yudiol.jobsearchplatform.mapper.UserMapper;
//import com.yudiol.jobsearchplatform.model.User;
//import com.yudiol.jobsearchplatform.repository.UserRepository;
//import com.yudiol.jobsearchplatform.service.AuthService;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AuthService {
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//
//    @Transactional
//    public AuthResponseDto register(UserDto userDto) {
//        User user = userMapper.toUser(userDto);
//        userRepository.save(user);
//        return new AuthResponseDto(user.getId(), user.getEmail(), "token");
//    }
//
//    @Transactional
//    public AuthResponseDto login(AuthRequestDto authRequestDto) {
//        return null;
//    }
//}


import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.UserDto;
import com.yudiol.jobsearchplatform.mapper.UserMapper;
import com.yudiol.jobsearchplatform.model.User;
import com.yudiol.jobsearchplatform.repository.UserRepository;
import com.yudiol.jobsearchplatform.security.JwtTokenUtils;
import com.yudiol.jobsearchplatform.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponseDto createAuthToken(AuthRequestDto auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        User user = userRepository.findByEmail(auth.getUsername()).orElseThrow();
        return new AuthResponseDto(user.getId(), user.getEmail(), token);
    }

    @Transactional
    public AuthResponseDto register(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        userRepository.save(user);
        return new AuthResponseDto(user.getId(), user.getEmail(), user.getPassword());
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new UsernameNotFoundException(String.format("UserDetailsService: пользователь с id '%s' не найден", id)));
    }
}
