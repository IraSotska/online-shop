package com.iryna.controller;

import com.iryna.entity.Product;
import com.iryna.security.Session;
import com.iryna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final UserService userService;

    @Autowired
    CartController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    protected String getCartBySession(HttpServletRequest req, Model model) {

        Session session = (Session) req.getAttribute("session");
        if (session != null) {
            List<Product> productsAtCart = session.getCart();
            model.addAttribute("products", productsAtCart);
        }
        return "product_cart";
    }

    @PostMapping("/add")
    protected void addToCart(@CookieValue("user-token") String token,
                             @RequestParam Integer id,
                             HttpServletResponse resp) throws IOException {

        userService.addProductToChart(token, id);
        resp.sendRedirect("/products");
    }

    @PostMapping("/remove")
    protected void removeFromCart(@CookieValue("user-token") String token,
                                  @RequestParam Integer id,
                                  HttpServletResponse resp) throws IOException {

        userService.removeProductFromChart(token, id);
        resp.sendRedirect("/cart");
    }
}
