package com.resmed.rpsgame.web.security;

import com.resmed.rpsgame.db.entity.Player;
import com.resmed.rpsgame.db.repository.PlayerRepository;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PlayerRepository playerRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        Player player = playerRepository.findByUsername(username);
        if (player == null) {
            throw new AuthenticationServiceException("Player not found with username:" + username);
        }

        if (!encoder.matches(password, player.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}