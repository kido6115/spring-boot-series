package com.sungyeh.security;

import com.sungyeh.service.RecaptchaService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自訂義security provider manager
 *
 * @author sungyeh
 */
@Component
public class CustomProvider extends AbstractUserDetailsAuthenticationProvider {

    /**
     * 驗證recaptcha
     */

    @Resource
    private RecaptchaService recaptchaService;

    /**
     * 使用者資訊
     */
    @Resource
    private UserDetailsService userDetailsService;


    /**
     * {@inheritDoc}
     * <p>
     * 同DaoAuthenticationProvider追加recaptcha驗證
     *
     * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            if (!new BCryptPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Failed to authenticate since password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            RecaptchaAuthenticationDetails details = (RecaptchaAuthenticationDetails) authentication.getDetails();
//            if (!this.recaptchaService.verify(details.getToken())) {
//                this.logger.debug("Failed to authenticate recaptcha");
//                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * 同DaoAuthenticationProvider獲得使用者資訊
     *
     * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
     */
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        try {
            UserDetails loadedUser = userDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
            } else {
                return loadedUser;
            }
        } catch (InternalAuthenticationServiceException var5) {
            throw var5;
        } catch (Exception var6) {
            throw new InternalAuthenticationServiceException(var6.getMessage(), var6);
        }
    }
}
