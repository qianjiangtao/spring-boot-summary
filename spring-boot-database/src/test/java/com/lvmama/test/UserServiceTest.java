package com.lvmama.test;

import com.lvmama.tony.App;
import com.lvmama.tony.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        for (int i = 1; i < 10; i++) {
            userService.create("jack_" + i, i * i);
        }
    }

    @Test
    public void deleteUserByUserName(){
        userService.deleteByUserName("jack_1");
    }

}
