package com.lvmama.tony.service.repository;

import com.lvmama.tony.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * <p>
 *
 * @author Tony-J
 * @date 2018/12/25
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
