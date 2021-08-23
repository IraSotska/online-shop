package com.iryna.service;

import com.iryna.db.UserDao;
import com.iryna.entity.Product;
import com.iryna.entity.User;
import com.iryna.security.SecurityService;
import java.util.List;

public class UserService {

    private UserDao userDao;
    private SecurityService securityService;
    private ProductService productService;

    public void removeProductFromChart(String token, int productId) {
        List<Product> cart = securityService.getChartByToken(token);
        cart.remove(productService.findById(productId));
    }

    public void addProductToChart(String token, int productId) {
        System.out.println("token " + token);
        System.out.println(securityService);
        List<Product> cart = securityService.getChartByToken(token);
        cart.add(productService.findById(productId));
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
