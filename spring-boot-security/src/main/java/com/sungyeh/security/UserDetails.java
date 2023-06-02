package com.sungyeh.security;

import com.sungyeh.domain.Person;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 擴充UserDetails
 *
 * @author sungyeh
 */
@EqualsAndHashCode(callSuper = true)
public class UserDetails extends org.springframework.security.core.userdetails.User {

    /**
     * serialVersionUID
     * 這個屬性是為了在反序列化時確保類版本的兼容性。
     */
    private static final long serialVersionUID = 6260992542369648223L;

    /**
     * 使用者資訊
     */
    @Getter
    private final Person user;
    /**
     * 使用者角色
     */
    @Getter
    private final List<String> roles;

    /**
     * Constructor
     *
     * @param user  使用者資訊
     * @param roles 使用者角色
     */
    public UserDetails(Person user, List<String> roles) {
        super(user.getUsername(), user.getPassword(),
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.user = user;
        this.roles = roles;
    }

    /**
     * Constructor
     *
     * @param user                  使用者資訊
     * @param roles                 使用者角色
     * @param enabled               是否啟用
     * @param accountNonExpired     帳號是否過期
     * @param credentialsNonExpired 認證是否過期
     * @param accountNonLocked      是否鎖定
     */
    public UserDetails(Person user, List<String> roles, boolean enabled,
                       boolean accountNonExpired, boolean credentialsNonExpired,
                       boolean accountNonLocked) {
        super(user.getUsername(), user.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.user = user;
        this.roles = roles;
    }
}
