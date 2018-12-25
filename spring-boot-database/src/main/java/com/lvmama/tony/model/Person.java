package com.lvmama.tony.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/25
 */
@Setter
@Getter
@Entity
@ToString
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    public Person() {
    }

    public Person(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
