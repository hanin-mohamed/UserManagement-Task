package com.task.employeemanagement.users.mapper;

import com.task.employeemanagement.common.dto.UserCreateRequest;
import com.task.employeemanagement.common.dto.UserResponse;
import com.task.employeemanagement.common.dto.UserUpdateRequest;
import com.task.employeemanagement.config.MapstructConfig;
import com.task.employeemanagement.users.entity.User;
import org.mapstruct.*;

@Mapper(config = MapstructConfig.class)
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "jwtTokens", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    User toEntity(UserCreateRequest dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "salary", source = "salary")
    void update(@MappingTarget User entity, UserUpdateRequest dto);

    UserResponse toResponse(User entity);
}