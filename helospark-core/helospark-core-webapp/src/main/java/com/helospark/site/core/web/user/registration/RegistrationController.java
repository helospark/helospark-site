package com.helospark.site.core.web.user.registration;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.AuthorityEntity;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;
import com.helospark.site.core.service.article.user.repository.AuthorityEntityRepository;

@RestController
@RequestMapping("/users")
public class RegistrationController {
    private ApplicationUserRepository applicationUserRepository;
    private AuthorityEntityRepository authorityEntityRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationController(ApplicationUserRepository applicationUserRepository, AuthorityEntityRepository authorityEntityRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.authorityEntityRepository = authorityEntityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/register")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAuthorities(createUserAuthority());
        applicationUserRepository.save(user);
    }

    private List<AuthorityEntity> createUserAuthority() {
        AuthorityEntity role = authorityEntityRepository.findByRole("ROLE_USER");
        return Collections.singletonList(role);
    }
}