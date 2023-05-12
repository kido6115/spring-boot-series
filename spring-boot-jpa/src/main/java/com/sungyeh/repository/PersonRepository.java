package com.sungyeh.repository;

import com.sungyeh.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 *
 * @author sungyeh
 */
public interface PersonRepository extends JpaRepository<Person, String> {

    Person findByUsername(String username);
}
