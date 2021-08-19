package com.iryna.web.servlet;

import com.iryna.creator.HtmlCreator;
import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class EditProductsListServlet extends HttpServlet {

    private ProductService productService = ServiceLocator.getService(ProductService.class);;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=utf-8");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("products", productService.findAll());
        resp.getWriter().println(HtmlCreator.generatePage(templateData, "/edit_product_list.html"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter("method").equals("DELETE")) {
            productService.removeProduct(Long.parseLong(req.getParameter("id")));
        }
        resp.sendRedirect("/product/editor");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        productService.updateProduct(Product.builder()
                .id(Long.parseLong(req.getParameter("productId")))
                .name(req.getParameter("productName"))
                .price(Double.parseDouble(req.getParameter("productPrice")))
                .productDescription(String.valueOf(req.getParameter("productDescription")))
                .creationDate(LocalDateTime.now())
                .build());
    }
}
