<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script src="https://accounts.google.com/gsi/client" async defer></script>
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
            <div class="d-flex  flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">OpenID</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/resources/templates/open-id.ftl">
                                頁面資訊 </a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/java/com/sungyeh/web/IndexController.java">
                                轉址後處理</a>
                        </div>
                    </div>
                </div>
            </div>
            <p>整合Google新的GSI來取得使用者資訊, 以及傳統的Oauth2.0進行登入獲得access token來取得基本資訊</p>

            <h5>GSI</h5>
            <div id="g_id_onload"
                 data-client_id="${clientId}"
                 data-context="signin"
                 data-ux_mode="popup"
                 data-login_uri="${systemUrl}/google/gsi"
                 data-auto_prompt="false">
            </div>

            <div class="g_id_signin"
                 data-type="standard"
                 data-shape="rectangular"
                 data-theme="outline"
                 data-text="signin_with"
                 data-size="large"
                 data-logo_alignment="left">
            </div>
            <h5>Google OpenID Connect</h5>
            <div class="row">
                <div class="col-md-3">
                    <a class="btn btn-outline-dark"
                       href="https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${clientId}&scope=openid email&redirect_uri=${redirect}&state=${state}&nonce=${nonce}"
                       role="button" style="text-transform:none">
                        <img width="20px" style="margin-bottom:3px; margin-right:5px" alt="Google sign-in"
                             src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/512px-Google_%22G%22_Logo.svg.png"/>
                        Login with Google
                    </a>
                </div>
            </div>
            <h5>Line OpenID Connect</h5>
            <a href="https://access.line.me/oauth2/v2.1/authorize?response_type=code&client_id=${lineClientId}&redirect_uri=${lineRedirect}&state=${state}&scope=profile openid&nonce=${nonce}">
                <img src="<@spring.url '/img/btn_login_base.png' />"/></a>

        </main>
    </div>
</div>
</body>

</html>
