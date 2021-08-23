package com.iryna.controller;

import com.iryna.creator.HtmlCreator;
import com.iryna.entity.Product;
import com.iryna.security.Session;
import com.iryna.service.UserService;
import com.iryna.web.parser.CookieParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private UserService userService;

    @Autowired
    CartController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    protected void getCartBySession(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Session session = (Session) req.getAttribute("session");
        resp.setContentType("text/html;charset=utf-8");
        Map<String, Object> templateData = new HashMap<>();

        if(session != null) {
            List<Product> productsAtCart = session.getCart();
            templateData.put("products", productsAtCart);
        }
        resp.getWriter().println(HtmlCreator.generatePage(templateData, "/product_cart.html"));
    }

    @PostMapping("/add")
    protected void addToCart(@RequestParam Integer id,
            HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String token = CookieParser.getTokenFromCookies(req.getCookies());
        userService.addProductToChart(token, id);
        resp.sendRedirect("/products");
    }

    @PostMapping("/remove")
    protected void removeFromCart(@RequestParam Integer id, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String token = CookieParser.getTokenFromCookies(req.getCookies());
        userService.removeProductFromChart(token, id);
        resp.sendRedirect("/cart");
    }
}
