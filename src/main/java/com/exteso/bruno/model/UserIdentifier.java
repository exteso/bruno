package com.exteso.bruno.model;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;

public class UserIdentifier {

    private final String provider;
    private final String username;

    private UserIdentifier(String provider, String username) {
        this.provider = provider;
        this.username = username;
    }

    public static UserIdentifier from(Authentication authentication) {
        return from((Principal) authentication.getPrincipal());
    }

    public static UserIdentifier from(Principal principal) {
        String[] pu = StringUtils.split(principal.getName(), ":", 2);
        String provider = pu[0];
        String username = pu[1];
        return new UserIdentifier(provider, username);
    }

    public String getProvider() {
        return provider;
    }

    public String getUsername() {
        return username;
    }
}
