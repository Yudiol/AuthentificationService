package com.yudiol.jobsearchplatform.service;//package com.yudiol.jobsearchplatform.service;

import com.yudiol.jobsearchplatform.dto.AuthRequestRegDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.model.User;

public interface AuthService {
    AuthResponseDto register(AuthRequestRegDto userDto);

    AuthResponseDto createAuthToken(String username,String password);

    User findById(Long id, String email);

    String getJwtToken(String username);
}
