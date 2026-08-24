package project.bookingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.bookingservice.config.MapperConfig;
import project.bookingservice.dto.user.UpdateCurrentUser;
import project.bookingservice.dto.user.UpdateUserRoleDto;
import project.bookingservice.dto.user.UserDto;
import project.bookingservice.dto.user.UserRegistrationDto;
import project.bookingservice.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationDto registrationDto);

    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUserRole(@MappingTarget User user, UpdateUserRoleDto userRoleDto);

    void updateCurrentUser(@MappingTarget User user, UpdateCurrentUser currentUser);
}
