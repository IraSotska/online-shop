package com.iryna.web.servlet;

import com.iryna.creator.HtmlCreator;
import com.iryna.loader.SettingsLoader;
import com.iryna.security.SecurityService;
import com.iryna.service.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private SecurityService securityService = ServiceLocator.getService(SecurityService.class);
    private SettingsLoader settingsLoader = ServiceLocator.getService(SettingsLoader .class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> data = new HashMap<>();
        response.getWriter().write(HtmlCreator.generatePage(data, "/login.html"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = securityService.login(request.getParameter("name"), request.getParameter("password"));
        if (token != null) {
            Cookie cookie = new Cookie("user-token", token);
            cookie.setMaxAge(settingsLoader.getTimeToLiveSession());
            response.addCookie(cookie);
            log.info("Login successful");
            response.sendRedirect("/products");
        }
    }
}
