package com.db.hackathon.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Override
    public Optional getCurrentAuditor() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
            return Optional.of("anonymousUser");
        Optional<String> result =  Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(User.class::cast).map( u-> u.getUsername());
        return result;

    }
}
