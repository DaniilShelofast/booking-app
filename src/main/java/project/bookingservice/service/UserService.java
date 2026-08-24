package project.bookingservice.service;

import project.bookingservice.dto.user.UpdateCurrentUser;
import project.bookingservice.dto.user.UpdateUserRoleDto;
import project.bookingservice.dto.user.UserDto;
import project.bookingservice.model.Role;

public interface UserService {
    UserDto updateRole(Long id, UpdateUserRoleDto userRoleDto);

    UserDto getCurrentUser();

    UserDto updateCurrentUser(UpdateCurrentUser currentUser);
}
