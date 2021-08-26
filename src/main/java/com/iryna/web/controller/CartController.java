package com.iryna.web.controller;

import com.iryna.entity.Product;
import com.iryna.security.Session;
import com.iryna.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final UserService userService;

    @GetMapping
    public String getCartBySession(HttpServletRequest req, Model model) {

        Session session = (Session) req.getAttribute("session");
        if (session != null) {
            List<Product> productsAtCart = session.getCart();
            model.addAttribute("products", productsAtCart);
        }
        return "product_cart";
    }

    @PostMapping("/add")
    public String addToCart(@CookieValue("user-token") String token,
                             @RequestParam Integer id) {
        userService.addProductToChart(token, id);
        return "redirect:/products";
    }

    @PostMapping("/remove")
    public String removeFromCart(@CookieValue("user-token") String token,
                                  @RequestParam Integer id) {
        userService.removeProductFromChart(token, id);
        return "redirect:/cart";
    }
}
