package com.iryna.db.jdbc;

import com.iryna.db.UserDao;
import com.iryna.entity.Role;
import com.iryna.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private static final String GET_USER_BY_NAME_QUERY = "SELECT role, encrypted_password, generated_salt FROM users " +
            "WHERE name = ?;";

    @Override
    public User getUserByName(String name) {

        return jdbcTemplate.queryForObject(GET_USER_BY_NAME_QUERY, new Object[]{name}, (resultSet, rowNum) ->
                User.builder()
                        .userName(name)
                        .generatedSalt(resultSet.getString("generated_salt"))
                        .password(resultSet.getString("encrypted_password"))
                        .role(Role.valueOf(resultSet.getString("role").toUpperCase()))
                        .build());
    }
}
