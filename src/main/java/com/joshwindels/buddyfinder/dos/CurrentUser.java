package com.joshwindels.buddyfinder.dos;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentUser {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String userId) {
        this.username = userId;
    }
}
