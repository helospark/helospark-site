package com.helospark.site.core.web.user.registration.converter;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.AuthorityEntity;
import com.helospark.site.core.service.article.user.repository.AuthorityEntityRepository;
import com.helospark.site.core.web.user.registration.domain.RegistrationForm;

@Component
public class ApplicationUserConverter {
    private AuthorityEntityRepository authorityEntityRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserConverter(AuthorityEntityRepository authorityEntityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityEntityRepository = authorityEntityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ApplicationUser convert(RegistrationForm form) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(form.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
        user.setAuthorities(createUserAuthority());
        return user;
    }

    private List<AuthorityEntity> createUserAuthority() {
        AuthorityEntity role = authorityEntityRepository.findByRole("ROLE_USER");
        return Collections.singletonList(role);
    }
}
