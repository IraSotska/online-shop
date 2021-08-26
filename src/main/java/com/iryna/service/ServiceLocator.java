package com.iryna.service;

import com.iryna.db.jdbc.JdbcProductDao;
import com.iryna.db.jdbc.JdbcUserDao;
import com.iryna.loader.SettingsLoader;
import com.iryna.security.PasswordEncryptor;
import com.iryna.security.SecurityService;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static Map<Class<?>, Object> SERVICES = new HashMap<>();

    static {
        SettingsLoader settingsLoader = new SettingsLoader("config.properties");
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(settingsLoader.getUrl());
        pgSimpleDataSource.setPassword(settingsLoader.getPassword());
        pgSimpleDataSource.setUser(settingsLoader.getUser());

        // DAO
//        JdbcProductDao jdbcProductDao = new JdbcProductDao(pgSimpleDataSource);
//        JdbcUserDao jdbcUserDao = new JdbcUserDao();

        // Service
        UserService userService = new UserService();
        SecurityService securityService = new SecurityService();
        ProductService productService = new ProductService();
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();

//        securityService.setSettingsLoader(settingsLoader);
        securityService.setUserService(userService);
        securityService.setPasswordEncryptor(passwordEncryptor);
//        userService.setUserDao(jdbcUserDao);
        userService.setSecurityService(securityService);
        userService.setProductService(productService);
//        productService.setProductDao(jdbcProductDao);

        addService(ProductService.class, productService);
        addService(UserService.class, userService);
        addService(PasswordEncryptor.class, passwordEncryptor);
        addService(SecurityService.class, securityService);
        addService(SettingsLoader.class, settingsLoader);
    }

    public static <T> T getService(Class<T> serviceType) {
        return serviceType.cast(SERVICES.get(serviceType));
    }

    public static void addService(Class<?> serviceName, Object service) {
        SERVICES.put(serviceName, service);

    }
}
