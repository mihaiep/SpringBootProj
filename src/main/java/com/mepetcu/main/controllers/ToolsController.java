package com.mepetcu.main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import com.mepetcu.main.elements.Result;
import org.springframework.web.bind.annotation.*;
import com.mepetcu.main.services.ThymeleafService;
import com.mepetcu.main.interfaces.ThymeleafElement;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(path = "/tools")
public class ToolsController extends GenericController {

    private final ThymeleafService service;

    @Autowired
    public ToolsController(@Qualifier("resultService") ThymeleafService service) {
        this.service = service;
    }

    @GetMapping(path = "")
    public String getEndpointsPage(Model model, Authentication authentication) {
        setupPage(model, authentication);
        ThymeleafElement element = service.getNext();
        if (element != null) {
            model.addAttribute(element.getID(), element.getValue());
        }
        return "tools.html";
    }

    @ResponseBody
    @PostMapping(path = "/random")
    public void getRandomString(@RequestParam Integer length, HttpServletResponse httpServlet) throws IOException {
        if (length != null) {
            Random random = new Random(System.currentTimeMillis());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < length; i++)
                stringBuilder.append((char) (97 + random.nextInt(26)));
            service.add(new Result("getRandomString", stringBuilder.toString()));
        }
        httpServlet.sendRedirect("/tools");
    }

    @ResponseBody
    @PostMapping(path = "/password")
    public void getPassword(@RequestParam Integer lowerCaseLen, @RequestParam Integer upperCaseLen, @RequestParam Integer numbersLen, @RequestParam Integer specialCharsLen, HttpServletResponse httpServlet) throws IOException {
        char[] specialChars = {'`', '-', '=', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '[', ']', ';', '\'', '\\', ',', '.', '/', '{', '}', ':', '"', '|', '<', '>', '?'};
        Random random = new Random(System.currentTimeMillis());
        List<Character> characterList = new ArrayList<>();
        if (lowerCaseLen != null) {
            for (int i = 0; i < lowerCaseLen; i++)
                characterList.add((char) (97 + random.nextInt(26)));
        }
        if (upperCaseLen != null) {
            for (int i = 0; i < upperCaseLen; i++)
                characterList.add((char) (65 + random.nextInt(26)));
        }
        if (numbersLen != null) {
            for (int i = 0; i < numbersLen; i++)
                characterList.add((char) (48 + random.nextInt(10)));
        }
        if (specialCharsLen != null) {
            for (int i = 0; i < specialCharsLen; i++)
                characterList.add(specialChars[random.nextInt(specialChars.length)]);
        }
        if (characterList.size() > 0) {
            Collections.shuffle(characterList);
            service.add(new Result("getPassword", characterList.stream().map(String::valueOf).collect(Collectors.joining())));
        }
        httpServlet.sendRedirect("/tools");
    }

    @ResponseBody
    @PostMapping(path = "/encode")
    void getEncode(@RequestParam String toEncode, HttpServletResponse httpServlet) throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        service.add(new Result("getEncode", bCryptPasswordEncoder.encode(toEncode)));
        httpServlet.sendRedirect("/tools");
    }

    @ResponseBody
    @PostMapping(path = "/check")
    void checkPasswords(@RequestParam String plain, @RequestParam String encoded, HttpServletResponse httpServlet) throws IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        service.add(new Result("checkPasswords", Boolean.toString(bCryptPasswordEncoder.matches(plain, encoded))));
        httpServlet.sendRedirect("/tools");
    }

}
