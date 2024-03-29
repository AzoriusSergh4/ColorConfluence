package com.cc.security;

import com.cc.security.user.UserComponent;
import com.cc.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserComponent userComponent;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var user = userRepository.findByEmailOrUsername(authentication.getName(), authentication.getName());

        if (user == null) {
            throw new BadCredentialsException("User not found");
        }

        if (!user.isEnabled()) {
            throw new BadCredentialsException("User is not enabled yet");
        }

        String password = (String) authentication.getCredentials();
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        userComponent.setLoggedUser(user);
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        return new UsernamePasswordAuthenticationToken(user.getUsername(), password, roles);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
