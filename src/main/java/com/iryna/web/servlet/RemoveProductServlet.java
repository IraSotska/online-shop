package com.iryna.web.servlet;

import com.iryna.service.ProductService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        productService.removeProduct(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("/product/editor");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}