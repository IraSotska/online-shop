package com.iryna.controller;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    protected String findAll(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product_list";
    }

    @GetMapping("/search")
    protected String search(@RequestParam String searchingProduct, Model model) {
        model.addAttribute("products", productService.getSearchedProducts(searchingProduct));
        return "product_list";
    }

    @GetMapping("/create")
    protected String createProduct() {
        return "add_product_page";
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
    protected String editProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "edit_product_list";
    }

    @GetMapping("/edit/product")
    protected String doGet(@RequestParam Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "edit_product";
    }

    @PostMapping("/edit/product")
    protected void doPost(@RequestParam String name,
                          @RequestParam Long id,
                          @RequestParam Double price,
                          @RequestParam String description,
                          HttpServletResponse resp) throws IOException {

        Product product = Product.builder()
                .name(name)
                .id(id)
                .price(price)
                .productDescription(description)
                .build();

        productService.updateProduct(product);
        resp.sendRedirect("/products");
    }
}
