package com.iryna.controller;

import com.iryna.creator.HtmlCreator;
import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    protected String findAll(HttpServletResponse response, Model model) throws IOException {

//        response.setContentType("text/html;charset=utf-8");
//        Map<String, Object> templateData = new HashMap<>();
//        templateData.put("products", productService.findAll());
        model.addAttribute("products", productService.findAll());

//        response.getWriter().println(HtmlCreator.generatePage(templateData, "/product_list.html"));
        return "templates/product_list.html";
    }

    @GetMapping("/search")
    @ResponseBody
    protected String search(@RequestParam String searchingProduct) {

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("products", productService.getSearchedProducts(searchingProduct));
        String responce = (HtmlCreator.generatePage(templateData, "/product_list.html"));
        return responce;
    }

    @GetMapping("/create")
    protected void createProduct(HttpServletResponse response) throws IOException {

        Map<String, Object> data = new HashMap<>();
        response.getWriter().write(HtmlCreator.generatePage(data, "/add_product_page.html"));
    }

    @PostMapping("/create")
    protected void doPost(@RequestParam String productName,
                          @RequestParam String productDescription,
                          @RequestParam Double productPrice,
                          HttpServletResponse response) throws IOException {
        Product product = Product.builder()
                .price(productPrice)
                .name(productName)
                .productDescription(productDescription)
                .build();
        productService.createProduct(product);
        response.sendRedirect("/products");
    }

    @PostMapping("/remove")
    protected void removeProduct(@RequestParam Long id, HttpServletResponse resp) throws IOException {

        productService.removeProduct(id);
        resp.sendRedirect("/products/edit");
    }

    @GetMapping("/edit")
    protected void editProductList(HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=utf-8");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("products", productService.findAll());
        resp.getWriter().println(HtmlCreator.generatePage(templateData, "/edit_product_list.html"));
    }

    @GetMapping("/edit/product")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=utf-8");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("product", productService.findById(Integer.parseInt(req.getParameter("id"))));
        resp.getWriter().println(HtmlCreator.generatePage(templateData, "/edit_product.html"));
    }

    @PostMapping("/edit/product")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Product product = Product.builder()
                .name(req.getParameter("name"))
                .id(Long.parseLong(req.getParameter("id")))
                .price(Double.parseDouble(req.getParameter("price")))
                .productDescription(req.getParameter("description"))
                .build();

        productService.updateProduct(product);
        resp.sendRedirect("/products");
    }


}
