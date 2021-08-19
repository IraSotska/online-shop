package com.iryna.web.servlet;

import com.iryna.service.ProductService;
import com.iryna.service.ServiceLocator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProductServlet extends HttpServlet {

    private ProductService productService = ServiceLocator.getService(ProductService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        productService.removeProduct(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("/product/editor");
    }
}
