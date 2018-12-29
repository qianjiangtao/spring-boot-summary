package com.lvmama.tony;


import com.fasterxml.jackson.core.JsonParser;
import com.lvmama.tony.model.Account;
import com.lvmama.tony.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/24
 */
@SpringBootApplication
public class App {

    @Autowired
    private AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Object createAccount() {

        Account account = accountRepository.queryAccountById("934");
        System.out.println("======================================");
        System.out.println(account);
        System.out.println("======================================");
        return new Account();
    }
}
