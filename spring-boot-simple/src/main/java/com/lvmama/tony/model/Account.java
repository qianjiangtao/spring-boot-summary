package com.lvmama.tony.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/26
 */
@Setter
@Getter
@ToString
@Document(indexName = "bank",type = "account")
public class Account {

    @Id
    private String id;

    @Field
    private Integer account_number;
    @Field
    private Integer balance;
    @Field
    private String firstname;
    @Field
    private String lastname;
    @Field
    private Integer age;
    @Field
    private String gender;
    @Field
    private String address;
    @Field
    private String employer;
    @Field
    private String email;
    @Field
    private String city;
    @Field
    private String state;
}
