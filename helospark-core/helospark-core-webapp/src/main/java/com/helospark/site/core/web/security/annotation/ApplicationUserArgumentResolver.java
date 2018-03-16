package com.helospark.site.core.web.security.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;
import com.helospark.site.core.web.security.TokenUser;

@Component
public class ApplicationUserArgumentResolver implements HandlerMethodArgumentResolver {
    private ApplicationUserRepository userRepository;

    public ApplicationUserArgumentResolver(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(InjectLoggedInUser.class) &&
                parameter.getParameterType().equals(ApplicationUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserNotLoggedInException("User login is required for this handler");
        }
        TokenUser tokenUser = (TokenUser) authentication.getPrincipal();
        if (tokenUser == null) {
            throw new UserNotLoggedInException("Unknown authorization");
        }
        ApplicationUser foundUser = userRepository.findByUsername(tokenUser.getUserName());
        if (foundUser == null) {
            throw new UsernameNotFoundException("User is not found in database");
        }
        return foundUser;
    }

}
