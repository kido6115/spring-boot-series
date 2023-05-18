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
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Authorization/Resource Server</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="dropdown">
                        <a class="btn btn-sm btn-outline-secondary dropdown-toggle" href="#" role="button"
                           id="dropdownMenuLink"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Dropdown link
                        </a>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <a class="dropdown-item" href="#">Something else here</a>
                        </div>
                    </div>
                </div>
            </div>
            <p>透過Spring Security/Spring Authorization Server自訂符合OAuth2.0伺服器以及相關的grant flow
            </p>
            <h1>Authorization Code</h1>
            <a target="_blank"
               href="${authorizationServer}/oauth2/authorize?client_id=client&response_type=code&redirect_uri=${redirect}">認證伺服器登入</a>
            <p>一樣使用 user/1111 進行登入</p>
            <p>獲取access token</p>
            <pre>
                <code data-language="shell">
            curl --location --request POST '${authorizationServer}/oauth2/token' \
            --header 'Authorization: Basic Y2xpZW50OnBhc3N3b3Jk' \
            --form 'grant_type="authorization_code"' \
            --form 'code="${r"${code}"}"' \
            --form 'redirect_uri="${redirect}"'
                </code>
            </pre>
            <h1>Client Credentials</h1>
            <p>獲取access token</p>
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
