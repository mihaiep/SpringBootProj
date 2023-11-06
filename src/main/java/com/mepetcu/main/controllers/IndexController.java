package com.mepetcu.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import com.mepetcu.main.services.ThymeleafService;
import com.mepetcu.main.interfaces.ThymeleafElement;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController extends GenericController {

    private final ThymeleafService service;

    @Autowired
    public IndexController(@Qualifier("tipsService") ThymeleafService service) {
        this.service = service;
    }


    @GetMapping(path = "/")
    public void toHome(HttpServletResponse http) throws IOException {
        http.sendRedirect("/index");
    }

    @GetMapping(path = "/index")
    public String getHomePage(Model model, Authentication authentication) {
        setupPage(model, authentication);
        ThymeleafElement element = service.getNext();
        model.addAttribute(element.getID(), element.getValue());
        return "index.html";
    }

    @ResponseBody
    @GetMapping(path = "/denied")
    public String accessDeniedPage() {
        return "<h1>You do not have permission to access this page.</h1>";
    }

}
