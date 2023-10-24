package com.yudiol.jobsearchplatform.service;

import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.UserDto;

public interface AuthService {
    AuthResponseDto register(UserDto userDto);

    AuthResponseDto login(AuthRequestDto authRequestDto);
}
