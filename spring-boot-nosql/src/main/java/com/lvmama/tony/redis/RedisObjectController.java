package com.lvmama.tony.redis;

import com.lvmama.tony.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/28
 */
@RestController
public class RedisObjectController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/queryByName/{username}")
    public Object queryByName(@PathVariable String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @PutMapping("/addUser")
    public void addUser(@RequestParam String username, @RequestParam String nickname) {
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        redisTemplate.opsForValue().set(username, user);
    }
}
