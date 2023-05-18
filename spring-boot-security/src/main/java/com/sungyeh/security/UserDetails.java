package com.sungyeh.security;

import com.sungyeh.domain.Person;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 擴充UserDetails
 */
public class UserDetails extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 6260992542369648223L;

    private Person user;
    private List<String> roles;

    public UserDetails(Person user, List<String> roles) {
        super(user.getUsername(), user.getPassword(),
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.user = user;
        this.roles = roles;
    }

    public UserDetails(Person user, List<String> roles, boolean enabled,
                       boolean accountNonExpired, boolean credentialsNonExpired,
                       boolean accountNonLocked) {
        super(user.getUsername(), user.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.user = user;
        this.roles = roles;
    }

    public Person getUser() {
        return user;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object rhs) {
        return super.equals(rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, roles);
    }
}
