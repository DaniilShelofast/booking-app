package project.bookingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.bookingservice.dto.user.UpdateCurrentUser;
import project.bookingservice.dto.user.UpdateUserRoleDto;
import project.bookingservice.dto.user.UserDto;
import project.bookingservice.exception.EntityNotFoundException;
import project.bookingservice.mapper.UserMapper;
import project.bookingservice.model.Role;
import project.bookingservice.model.User;
import project.bookingservice.repository.UserRepository;
import project.bookingservice.service.UserService;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto updateRole(Long id, UpdateUserRoleDto role) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id " + id)
        );
        userMapper.updateUserRole(user, role);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getCurrentUser() {
        User user = getUser();
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateCurrentUser(UpdateCurrentUser currentUser) {
        User user = getUser();
        userMapper.updateCurrentUser(user, currentUser);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
