package com.iryna.controller;

import com.iryna.creator.HtmlCreator;
import com.iryna.loader.SettingsLoader;
import com.iryna.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final SecurityService securityService;
    private final SettingsLoader settingsLoader;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LoginController(SecurityService securityService, SettingsLoader settingsLoader) {
        this.securityService = securityService;
        this.settingsLoader = settingsLoader;
    }

    @GetMapping("/login")
    protected void getLoginPage(HttpServletResponse response) throws IOException {

        Map<String, Object> data = new HashMap<>();
        response.getWriter().write(HtmlCreator.generatePage(data, "/login.html"));
    }

    @PostMapping("/login")
    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = securityService.login(request.getParameter("name"), request.getParameter("password"));
        if (token != null) {
            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(settingsLoader.getTimeToLiveSession());
            response.addCookie(cookie);
            log.info("Login successful");
            response.sendRedirect("/products");
        }
    }

    @GetMapping("/logout")
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    securityService.logout(cookie.getValue());
                }
            }
        }
        response.sendRedirect("/login");
    }
}
