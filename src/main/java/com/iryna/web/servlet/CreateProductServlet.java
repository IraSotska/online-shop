package com.iryna.web.servlet;

import com.iryna.creator.HtmlCreator;
import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CreateProductServlet extends HttpServlet {

    private ProductService productService = ServiceLocator.getService(ProductService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> data = new HashMap<>();
        response.getWriter().write(HtmlCreator.generatePage(data, "/add_product_page.html"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = new Product();
        product.setName(request.getParameter("productName"));
        product.setProductDescription(request.getParameter("productDescription"));
        product.setPrice(Double.parseDouble(request.getParameter("productPrice")));

        productService.createProduct(product);

        response.sendRedirect("/products");
    }
}