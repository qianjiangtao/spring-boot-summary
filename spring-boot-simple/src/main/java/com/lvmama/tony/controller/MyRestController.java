package com.lvmama.tony.controller;

import com.lvmama.tony.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/24
 */
@RestController
public class MyRestController {

    @GetMapping(value = "/queryUser/{id}")
    public Object queryUser(@PathVariable Integer id,String username) {
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        return user;
    }
}
