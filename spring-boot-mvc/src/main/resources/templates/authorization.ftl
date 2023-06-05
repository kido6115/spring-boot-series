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
            <div class="d-flex  flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Authorization/Resource Server</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/blob/master/authorization-server/src/main/java/com/sungyeh/config/AuthorizationServerConfig.java">認證伺服器Config</a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/java/com/sungyeh/config/ResourceServerConfig.java">資源伺服器Config</a>
                        </div>
                    </div>
                </div>
            </div>
            <p>透過Spring Security/Spring Authorization Server自訂符合OAuth2.0伺服器以及相關的grant flow
            </p>
            <h5>Authorization Code</h5>

            <a target="_blank" class="btn btn-outline-primary"
               href="${authorizationServer}/oauth2/authorize?client_id=client&response_type=code&redirect_uri=${redirect}">認證伺服器使用
                user/1111 進行登入</a>
            <h6>獲取access token</h6>
            <pre>
                <code data-language="shell">
            curl --location --request POST '${authorizationServer}/oauth2/token' \
            --header 'Authorization: Basic Y2xpZW50OnBhc3N3b3Jk' \
            --form 'grant_type="authorization_code"' \
            --form 'code="${r"${code}"}"' \
            --form 'redirect_uri="${redirect}"'
                </code>
            </pre>
            <h5>Client Credentials</h5>
            <h6>獲取access token</h6>
            <pre>
                <code data-language="shell">
            curl --location --request POST '${authorizationServer}/oauth2/token' \
            --header 'Authorization: Basic Y2xpZW50OnBhc3N3b3Jk' \
            --form 'grant_type="client_credentials"'
                </code>
            </pre>
        </main>
    </div>
</div>
</body>

</html>
