package com.iryna.web.controller;

import com.iryna.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;

@AllArgsConstructor
@Controller
public class LogoutController {

    private SecurityService securityService;

    @GetMapping("/logout")
    public String logout(@CookieValue("user-token") Cookie cookie) {
        securityService.logout(cookie.getValue());
        return "redirect:/login";
    }
}
