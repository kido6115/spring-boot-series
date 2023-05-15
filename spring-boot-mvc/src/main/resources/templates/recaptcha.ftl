<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>

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
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Recaptcha</h1>
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
            <h2>重新定義Spring Security Provider Manager及AuthenticationDetails</h2>
            <p>自訂義驗證流程加強原security僅驗證使用者帳號密碼, 達到多因子驗證</p>

            <h3>自訂義AuthenticationProvider</h3>
            <pre>
                <code data-language="java">
            @Component
            public class CustomProvider extends AbstractUserDetailsAuthenticationProvider {


                @Resource
                private RecaptchaService recaptchaService;
                @Resource
                private UserDetailsService userDetailsService;


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
                        if (!this.recaptchaService.verify(details.getToken())) {
                            this.logger.debug("Failed to authenticate recaptcha");
                            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                        }
                    }
                }

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
                </code>
            </pre>
            <h3>自訂義AuthenticationDetailsSource</h3>
            <pre>
                <code data-language="java">
        @Component
        public class RecaptchaAuthenticationDetailsSource
                implements AuthenticationDetailsSource&lt;HttpServletRequest, RecaptchaAuthenticationDetails&gt; {

            @Override
            public RecaptchaAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {
                return new RecaptchaAuthenticationDetails(httpServletRequest);
            }
        }
                </code>
            </pre>
            <h3>自訂義AuthenticationDetails</h3>
            <pre>
                <code data-language="java">
        @EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
        public class RecaptchaAuthenticationDetails extends WebAuthenticationDetails {

            @Getter
            private String token;


            public RecaptchaAuthenticationDetails(HttpServletRequest request) {
                super(request);
                this.token = request.getParameter("g-recaptcha-response");
            }
        }
                </code>
            </pre>
        </main>
    </div>
</div>
</body>

</html>
