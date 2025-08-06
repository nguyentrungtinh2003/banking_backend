package com.TrungTinhBackend.banking_backend.Service.Jwt;

import com.TrungTinhBackend.banking_backend.Entity.User;
import com.TrungTinhBackend.banking_backend.Exception.NotFoundException;
import com.TrungTinhBackend.banking_backend.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String citizenId) throws UsernameNotFoundException {
        User user = userRepository.findByCitizenId(citizenId);

        if(user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }
}
