package project.bookingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import project.bookingservice.dto.user.UpdateCurrentUser;
import project.bookingservice.dto.user.UpdateUserRoleDto;
import project.bookingservice.dto.user.UserDto;
import project.bookingservice.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    public UserDto updateUserRole(@PathVariable Long id,
                                  @Valid @RequestBody UpdateUserRoleDto userRoleDto) {
        return userService.updateRole(id, userRoleDto);
    }

    @PutMapping("/me")
    public UserDto updateCurrentUserProfile(@RequestBody UpdateCurrentUser currentUser) {
        return userService.updateCurrentUser(currentUser);
    }

    @GetMapping("/me")
    public UserDto getCurrentUserProfile() {
        return userService.getCurrentUser();
    }
}
