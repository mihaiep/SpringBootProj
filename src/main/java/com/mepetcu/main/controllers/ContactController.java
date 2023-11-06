package com.mepetcu.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;


@Controller
@RequestMapping(path = "/contact")
public class ContactController extends GenericController {

    @GetMapping(path = "")
    public String getContactPage(Model model, Authentication authentication) {
        setupPage(model, authentication);
        return "contact.html";
    }

}
