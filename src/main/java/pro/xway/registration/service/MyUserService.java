package pro.xway.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.xway.registration.dao.UserRepository;
import pro.xway.registration.model.MyUser;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MyUserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NotNull String email) {
        AtomicReference<MyUser> user = new AtomicReference<>();
        userRepository.findByUsername(email).ifPresent(user::set);
        if (user.get() == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return user.get();
    }

    public MyUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<MyUser> byUsername = userRepository.findByUsername(userDetails.getUsername());
            if (byUsername.isPresent())
                return byUsername.get();
        }
        return null;
    }
}
