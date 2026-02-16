package com.example.projetoes.security;

import com.example.projetoes.dao.UserRep;
import com.example.projetoes.domain.Role;
import com.example.projetoes.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRep userRep;

    public JwtUserDetailsService(UserRep userRep) {
        this.userRep = userRep;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User u = userRep.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        Collection<? extends GrantedAuthority> authorities = u.getRoles().stream()
                .map(Role::getName)
                .map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name) // proteção extra
                .map(SimpleGrantedAuthority::new)
                .toList();

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPasswordHash())
                .authorities(authorities)
                .build();
    }
}
