package com.yudiol.jobsearchplatform.service;//package com.yudiol.jobsearchplatform.service;
//
//import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
//import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
//import com.yudiol.jobsearchplatform.dto.UserDto;
//
//public interface AuthService {
//    AuthResponseDto register(UserDto userDto);
//
//    AuthResponseDto login(AuthRequestDto authRequestDto);
//}

import com.yudiol.jobsearchplatform.dto.AuthRequestDto;
import com.yudiol.jobsearchplatform.dto.AuthResponseDto;
import com.yudiol.jobsearchplatform.dto.UserDto;
import com.yudiol.jobsearchplatform.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthResponseDto register(UserDto userDto);

    AuthResponseDto createAuthToken(AuthRequestDto authenticationRequestDto);
    User findById(Long id);
}
