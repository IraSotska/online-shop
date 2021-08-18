package com.iryna.web.servlet;

import com.iryna.service.UserService;
import com.iryna.web.parser.CookieParser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveFromCartServlet extends HttpServlet {

    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String token = CookieParser.getTokenFromCookies(req.getCookies());
        userService.removeProductFromChart(token, Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/cart");
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
