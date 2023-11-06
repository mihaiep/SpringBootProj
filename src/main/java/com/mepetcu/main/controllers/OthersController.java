package com.mepetcu.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import com.mepetcu.main.elements.Result;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.mepetcu.main.services.ThymeleafService;
import com.mepetcu.main.interfaces.ThymeleafElement;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;

import java.io.File;
import java.util.Map;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(path = "/others")
public class OthersController extends GenericController {

    private final ThymeleafService thymeleafService;
    private final UserDetailsManager manager;

    @Autowired
    public OthersController(@Qualifier("resultService") ThymeleafService thymeleafService, UserDetailsManager manager) {
        this.thymeleafService = thymeleafService;
        this.manager = manager;
    }

    @GetMapping(path = "")
    public String getOthersPage(Model model, Authentication authentication) {
        setupPage(model, authentication);
        ThymeleafElement element = thymeleafService.getNext();
        if (element != null) {
            model.addAttribute(element.getID(), element.getValue());
        }
        return "others.html";
    }

    @ResponseBody
    @GetMapping(path = "/image/get={imgName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getSpringImage(@PathVariable String imgName, HttpServletResponse response) throws Exception {
        if (imgName.contains("/") || imgName.contains("\\")) throw new Exception("There are not allowed such characters.");
        Path path = Paths.get("target/classes/static/images/" + imgName);
        try {
            File file = new File(path.toString());
            byte[] bytes = Files.readAllBytes(file.toPath());
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            return bytes;
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new IOException(String.format("File: %s not found", path));
        }
    }

    @ResponseBody
    @PostMapping(path = "/hello/body")
    public String sayHelloBodyVariable(@RequestBody Map<String, String> body) {
        return "Hello, " + body.get("name") + "!";
    }

    @ResponseBody
    @GetMapping(path = "/hello/header")
    public String sayHelloHeaderVariable(@RequestHeader String name) {
        return "Hello, " + name + "!";
    }

    @ResponseBody
    @GetMapping(path = "/hello={name}")
    public String sayHelloPathVariable(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @ResponseBody
    @PostMapping(path = "/create-user")
    public void createUser(@RequestParam String name, @RequestParam String password, HttpServletResponse httpServlet) throws IOException {
        try {
            if (password.length() < 5) throw new Exception("Password is too short.");
            manager.createUser(User.withUsername(name).password(password).authorities("User").build());
            thymeleafService.add(new Result("createUser", "OK"));
        } catch (Exception e) {
            thymeleafService.add(new Result("createUser", "KO: " + e.getMessage()));
        }
        httpServlet.sendRedirect("/others");
    }
}
