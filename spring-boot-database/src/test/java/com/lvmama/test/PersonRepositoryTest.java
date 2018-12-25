package com.lvmama.test;

import com.lvmama.tony.App;
import com.lvmama.tony.model.Person;
import com.lvmama.tony.service.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void addPerson() {
        for (int i = 1; i < 10; i++) {
            personRepository.save(new Person("username-" + i, "nickname-" + i));
        }
    }

    @Test
    public void queryAll() {
        List<Person> list = personRepository.findAll();
        System.out.println("size:" + list.size());
    }
}
