package project.bookingservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.bookingservice.exception.EntityNotFoundException;
import project.bookingservice.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class CustomDetailsUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by username " + username)
        );
    }
}
