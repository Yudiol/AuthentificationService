package com.yudiol.jobsearchplatform.service.impl;

import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.UserDto;
import com.yudiol.jobsearchplatform.mapper.UserMapper;
import com.yudiol.jobsearchplatform.model.User;
import com.yudiol.jobsearchplatform.repository.UserRepository;
import com.yudiol.jobsearchplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponseDto register(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        userRepository.save(user);
        return new AuthResponseDto(user.getId(), user.getEmail(), "token");
    }

    @Transactional
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        return null;
    }
}
