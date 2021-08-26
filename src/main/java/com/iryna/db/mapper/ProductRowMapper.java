package com.iryna.db.mapper;

import com.iryna.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ProductRowMapper implements RowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        String productDescription = resultSet.getString("product_description");
        LocalDateTime localDateTime = resultSet.getTimestamp("creation_date").toLocalDateTime();

        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .creationDate(localDateTime)
                .productDescription(productDescription)
                .build();
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return mapRow(resultSet);
    }
}
