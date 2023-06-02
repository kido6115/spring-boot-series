package com.sungyeh.repository;

import com.sungyeh.domain.Person;

/**
 * UserRepository
 *
 * @author sungyeh
 */
public interface PersonRepository extends BaseRepository<Person, String> {

    /**
     * 透過帳號取得單筆資料
     *
     * @param username 帳號
     * @return 單筆資料
     */
    Person findByUsername(String username);
}
