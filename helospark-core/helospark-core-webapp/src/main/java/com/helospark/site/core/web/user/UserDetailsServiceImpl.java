package com.helospark.site.core.web.user;

import static java.util.Collections.singletonList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;

    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), getAuthority(applicationUser));
    }

    private List<SimpleGrantedAuthority> getAuthority(ApplicationUser applicationUser) {
        List<SimpleGrantedAuthority> foundAuthorities = applicationUser.getAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getRole()))
                .collect(Collectors.toList());
        if (foundAuthorities.isEmpty()) {
            return singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return foundAuthorities;
        }
    }

}
