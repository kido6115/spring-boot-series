package com.sungyeh.repository;

import com.sungyeh.domain.Person;

/**
 * UserRepository
 *
 * @author sungyeh
 */
public interface PersonRepository extends BaseRepository<Person, String> {

    Person findByUsername(String username);
}
