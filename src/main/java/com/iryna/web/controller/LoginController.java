package com.iryna.web.controller;

import com.iryna.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final SecurityService securityService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${timeToLiveSession}")
    private int timeToLiveSession;

    @Autowired
    LoginController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    protected String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    protected String login(@RequestParam String name,
                           @RequestParam String password,
                           HttpServletResponse response) {
        String token = securityService.login(name, password);
        if (token != null) {
            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(timeToLiveSession);
            response.addCookie(cookie);
            log.info("Login successful");
        }
        return "redirect:/products";
    }
}
