package com.yudiol.jobsearchplatform.mapper;

import com.yudiol.jobsearchplatform.dto.AuthRequestRegDto;
import com.yudiol.jobsearchplatform.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(AuthRequestRegDto userDto);
}
