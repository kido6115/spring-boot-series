package com.sungyeh.security;

import com.sungyeh.domain.Person;
import com.sungyeh.repository.PersonRepository;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * UserDetailsService 實作
 * 進行帳號基本驗證
 *
 * @author sungyeh
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = personRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found.");
        } else {
            boolean enable = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            List<String> roles = Arrays.asList("ROLE_ADMIN");
            return new com.sungyeh.security.UserDetails(user, roles,
                    enable, accountNonExpired, credentialsNonExpired, accountNonLocked);
        }
    }
}
