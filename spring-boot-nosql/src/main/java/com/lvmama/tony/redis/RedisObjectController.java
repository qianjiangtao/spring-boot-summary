package com.lvmama.tony.redis;

import com.lvmama.tony.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * redis object 测试
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/28
 */
@RestController
@RequestMapping(value = "/object")
public class RedisObjectController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/get/{username}")
    public Object get(@PathVariable String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @PutMapping("/put")
    public void put(String username, String nickname) {
        User user = new User(username, nickname);
        redisTemplate.opsForValue().set(username, user);
    }
}
