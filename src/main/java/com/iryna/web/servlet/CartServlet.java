package com.iryna.web.servlet;

import com.iryna.creator.HtmlCreator;
import com.iryna.entity.Product;
import com.iryna.security.Session;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Session session = (Session) req.getAttribute("session");
        resp.setContentType("text/html;charset=utf-8");
        Map<String, Object> templateData = new HashMap<>();

        if(session != null) {
            List<Product> productsAtCart = session.getCart();
            templateData.put("products", productsAtCart);
        }
        resp.getWriter().println(HtmlCreator.generatePage(templateData, "/product_cart.html"));
    }
}
