package com.iryna.web.controller;

import com.iryna.entity.Product;
import com.iryna.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product_list";
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchingProduct, Model model) {
        model.addAttribute("products", productService.getSearchedProducts(searchingProduct));
        return "product_list";
    }

    @GetMapping("/create")
    public String getCreateProductPage() {
        return "add_product_page";
    }

    @PostMapping("/create")
    public String postCreateProduct(@RequestParam String productName,
                          @RequestParam String productDescription,
                          @RequestParam Double productPrice) {
        Product product = Product.builder()
                .price(productPrice)
                .name(productName)
                .productDescription(productDescription)
                .build();
        productService.createProduct(product);
        return "redirect:/products";
    }

    @PostMapping("/remove")
    public String removeProduct(@RequestParam Long id) {
        productService.removeProduct(id);
        return "redirect:/products/edit";
    }

    @GetMapping("/edit")
    public String editProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "edit_product_list";
    }

    @GetMapping("/edit/product")
    public String doGet(@RequestParam Integer id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "edit_product";
    }

    @PostMapping("/edit/product")
    public String doPost(@RequestParam String name,
                          @RequestParam Long id,
                          @RequestParam Double price,
                          @RequestParam String description) {

        Product product = Product.builder()
                .name(name)
                .id(id)
                .price(price)
                .productDescription(description)
                .build();

        productService.updateProduct(product);
        return "redirect:/products";
    }
}
