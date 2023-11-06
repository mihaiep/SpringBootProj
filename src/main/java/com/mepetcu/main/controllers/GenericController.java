package com.mepetcu.main.controllers;

import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class GenericController {

    private String getAuthority(Collection<? extends GrantedAuthority> authorities) {
        GrantedAuthority auth = authorities.stream().findFirst().orElse(null);
        if (auth != null) return auth.getAuthority();
        return null;
    }

    protected void setupPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("authority", getAuthority(authentication.getAuthorities()));
            model.addAttribute("loggedindisplay", "display: none");
            model.addAttribute("loggedoutdisplay", "display: inherit");
        } else {
            model.addAttribute("loggedindisplay", "display: inherit");
            model.addAttribute("loggedoutdisplay", "display: none");
        }
    }

}
