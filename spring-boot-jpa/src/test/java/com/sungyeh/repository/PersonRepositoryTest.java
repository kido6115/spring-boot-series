package com.sungyeh.repository;

import com.sungyeh.domain.Person;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


/**
 * PersonRepositoryTest
 *
 * @author sungyeh
 */
@DisplayName("Person單元測試")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PersonRepositoryTest {

    @Resource
    private PersonRepository personRepository;

    @Test
    public void testFindByUsername() {
        Person person = personRepository.findByUsername("user");
        Assertions.assertNotNull(person);
        Assertions.assertNotNull(person);
        Assertions.assertEquals("user", person.getUsername());
        System.out.println(person.getRoles());
    }
}
