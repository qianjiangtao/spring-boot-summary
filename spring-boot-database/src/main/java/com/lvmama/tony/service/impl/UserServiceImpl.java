package com.lvmama.tony.service.impl;

import com.lvmama.tony.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(String username, Integer age) {
        jdbcTemplate.update("insert into USER(user_name, age) values(?, ?)", username, age);
    }

    @Override
    public void deleteByUserName(String username) {
        jdbcTemplate.update("delete from USER where user_name = ?", username);
    }
}
