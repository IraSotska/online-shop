package com.iryna.db.jdbc;

import com.iryna.db.ProductDao;
import com.iryna.db.mapper.ProductRowMapper;
import com.iryna.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;

@AllArgsConstructor
public class JdbcProductDao implements ProductDao {

    private static final String GET_ALL_PRODUCTS_QUERY = "SELECT * FROM products;";
    private static final String DELETE_QUERY = "DELETE FROM products WHERE id = ?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET name = ?, price = ?," +
            " product_description = ?, creation_date = ? WHERE id = ?";
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products(name, price, product_description, " +
            "creation_date) VALUES (?, ?, ?, ?);";
    private static final String FIND_PRODUCT_BY_ID_QUERY = "SELECT name, product_description, price, " +
            "creation_date FROM products WHERE id = ?;";

    private static final ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();
    private JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        return jdbcTemplate.query(GET_ALL_PRODUCTS_QUERY, PRODUCT_ROW_MAPPER);
    }

    @Override
    public Product findById(int id) {
        return jdbcTemplate.queryForObject(FIND_PRODUCT_BY_ID_QUERY, new Object[]{id}, (resultSet, rowNum) ->
                Product.builder()
                        .id(id)
                        .name(resultSet.getString("name"))
                        .price(resultSet.getDouble("price"))
                        .creationDate(resultSet.getTimestamp("creation_date").toLocalDateTime())
                        .productDescription(resultSet.getString("product_description"))
                        .build());
    }

    public void addProduct(Product product) {
        jdbcTemplate.update(ADD_PRODUCT_QUERY, product.getName(), product.getPrice(), product.getProductDescription(),
                new Timestamp(System.currentTimeMillis()));
    }

    public void updateProduct(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT_QUERY, product.getName(), product.getPrice(), product.getProductDescription(),
                new Timestamp(System.currentTimeMillis()), product.getId());
    }

    public void removeProduct(long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }
}
