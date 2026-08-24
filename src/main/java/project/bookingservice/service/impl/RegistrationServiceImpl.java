package project.bookingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.bookingservice.dto.user.UserDto;
import project.bookingservice.dto.user.UserRegistrationDto;
import project.bookingservice.exception.EntityNotFoundException;
import project.bookingservice.exception.RegistrationException;
import project.bookingservice.mapper.UserMapper;
import project.bookingservice.model.Role;
import project.bookingservice.model.RoleName;
import project.bookingservice.model.User;
import project.bookingservice.repository.RoleRepository;
import project.bookingservice.repository.UserRepository;
import project.bookingservice.service.RegistrationService;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDto register(UserRegistrationDto request) throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Can't register user");
        }
        User user = userMapper.toModel(request);
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Can't find role user "));
        user.setRoles(Set.of(role));
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
