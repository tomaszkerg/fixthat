package pl.tomek.fixthat.service.user;

import org.springframework.security.core.context.SecurityContextHolder;

public class ContextService {

    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
