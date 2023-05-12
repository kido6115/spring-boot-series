<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "meta.ftl" />
</head>
<body>
<#include "nav.ftl" />

<div class="container-fluid">
    <div class="row">
        <#include "sidebar.ftl" />

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div class="chartjs-size-monitor">
                <div class="chartjs-size-monitor-expand">
                    <div class=""></div>
                </div>
                <div class="chartjs-size-monitor-shrink">
                    <div class=""></div>
                </div>
            </div>
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Spring Security</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group mr-2">
                        <button type="button" class="btn btn-sm btn-outline-secondary">Share</button>
                        <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                    </div>
                    <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="feather feather-calendar">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                        </svg>
                        This week
                    </button>
                </div>
            </div>
            <h2>Config</h2>
            <p>配置config即可迅速作出需要登入驗證網站, 以下為此站所使用並含有以下功能
            </p>
            <ol>
                <li>CSRF預防</li>
                <li>XSS預防</li>
                <li>CORS同源設定</li>
                <li>ACL設定</li>
                還沒作
                <li>自訂義多因子驗證</li>
                <li>資料庫勾稽使用者</li>
            </ol>
            <pre>
                <code data-language="java">
                           @Configuration
                           @EnableWebSecurity
                           public class SecurityConfig {
                               @Resource
                               private UserDetailsService userDetailsService;

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


                           //                .authenticationDetailsSource(detailsSource)
                                           .and()
                                           .logout()
                                           .logoutUrl("/logout")
                                           .logoutSuccessUrl("/login")
                                           .and()
                                           .exceptionHandling()
                                           .and()
                           //                .authenticationProvider(provider)
                                           .csrf()
                                           .and()
                                           .userDetailsService(userDetailsService)
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
