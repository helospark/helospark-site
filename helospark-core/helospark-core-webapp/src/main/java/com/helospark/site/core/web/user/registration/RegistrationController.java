package com.helospark.site.core.web.user.registration;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;
import com.helospark.site.core.web.user.registration.converter.ApplicationUserConverter;
import com.helospark.site.core.web.user.registration.domain.RegistrationForm;

@RestController
@RequestMapping("/users")
public class RegistrationController {
    private ApplicationUserRepository applicationUserRepository;
    private ApplicationUserConverter applicationUserConverter;

    public RegistrationController(ApplicationUserRepository applicationUserRepository, ApplicationUserConverter applicationUserConverter) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserConverter = applicationUserConverter;
    }

    @PostMapping("/register")
    public void signUp(@RequestBody @Valid RegistrationForm form) {
        ApplicationUser applicationUser = applicationUserConverter.convert(form);
        applicationUserRepository.save(applicationUser);
    }

}