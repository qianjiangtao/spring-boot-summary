package com.lvmama.tony.model;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/28
 */
@Setter
@Getter
public class User {

    private String username;

    private String nickname;

    public User() {
    }

    public User(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
