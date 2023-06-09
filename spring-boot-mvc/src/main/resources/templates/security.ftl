<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
</head>
<body>
<#include "component/nav.ftl" />

<div class="container-fluid">
    <div class="row">
        <#include "component/sidebar.ftl" />

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div class="chartjs-size-monitor">
                <div class="chartjs-size-monitor-expand">
                    <div class=""></div>
                </div>
                <div class="chartjs-size-monitor-shrink">
                    <div class=""></div>
                </div>
            </div>
            <div class="d-flex flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Spring Security</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="dropdown">
                        <a class="btn btn-sm btn-outline-secondary dropdown-toggle" href="#" role="button"
                           id="dropdownMenuLink"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            原始碼
                        </a>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-security/src/main/java/com/sungyeh/config/SecurityConfig.java">Config</a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/tree/master/spring-boot-security/src/main/java/com/sungyeh/security">Security相關</a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/tree/master/spring-boot-security/src/main/java/com/sungyeh/security/CustomAuthorizationManager.java">ACLs</a>
                        </div>
                    </div>
                </div>
            </div>
            <h5>Config</h5>
            <p>配置config即可迅速作出需要登入驗證網站, 以下為此站所使用並含有以下功能
            </p>
            <ol>
                <li>CSRF預防</li>
                <li>XSS預防</li>
                <li>CORS同源設定</li>
                <li>ACL設定</li>
                <li>自訂義多因子驗證 : e.g. <a href="<@spring.url '/auth/recaptcha'/>">recaptcha實作</a></li>
                <li>資料庫勾稽使用者</li>
                <li>透過PasswordEncoder hash/crypt使用者密碼</li>

            </ol>
            <pre>
                <code data-language="java">
        @Configuration
        @EnableWebSecurity
        public class SecurityConfig {

            @Resource
            private CustomProvider provider;

            @Bean("backendFilterChain")
            public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(a -> a.requestMatchers("/auth/**").authenticated()
                                .requestMatchers("/h2/**").permitAll()
                                .anyRequest().permitAll())
                        .formLogin()
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/login/process").permitAll()
                        .defaultSuccessUrl("/index")
                        .failureUrl("/login")
                        .authenticationDetailsSource(new RecaptchaAuthenticationDetailsSource())
                        .and()
                        .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .and()
                        .exceptionHandling()
                        .and()
                        .authenticationProvider(provider)
                        .csrf()
                        .and()
                        .headers().frameOptions().sameOrigin()
                        .xssProtection().headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK);

                return http.build();
            }

            @Bean
            public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
            }


        }
                    </code>
            </pre>
        </main>
    </div>
</div>
</body>

</html>
