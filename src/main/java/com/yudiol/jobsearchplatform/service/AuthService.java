package com.yudiol.jobsearchplatform.service;//package com.yudiol.jobsearchplatform.service;

import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.UserDto;
import com.yudiol.jobsearchplatform.model.User;

public interface AuthService {
    AuthResponseDto register(UserDto userDto);

    AuthResponseDto createAuthToken(String username,String password);

    User findById(Long id);

    String getJwtToken(String username);
}
