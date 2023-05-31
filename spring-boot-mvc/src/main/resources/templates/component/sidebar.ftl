<#ftl output_format="HTML" />
<#assign menu=[
{
"title":"Spring",
"item":[
{"title":"MVC","path":"/auth/mvc","icon":"fa fa-desktop"},
{"title":"JPA","path":"/auth/jpa","icon":"fa fa-database"},
{"title":"Security","path":"/auth/security","icon":"fa fa-shield"},
{"title":"Authorization/Resource Server","path":"/auth/authorization","icon":"fa fa-server"},
{"title":"Swagger/SpringDoc","path":"/auth/swagger","icon":"fa fa-file-text"},
{"title":"WebSocket","path":"/auth/web-socket","icon":"fa fa-commenting"}
]
},
{
"title":"Google",
"item":
[
{"title":"Goolge Map API","path":"/auth/google-map","icon":"fa fa-map"},
{"title":"Cloud Vision API","path":"/auth/cloud-vision","icon":"fa fa-eye"},
{"title":"Dialogflow","path":"/auth/dialogflow","icon":"fa fa-android"},
{"title":"Recaptcha API","path":"/auth/recaptcha","icon":"fa fa-check"}
]
},
{
"title":"Other",
"item":
[
{"title":"OpenID","path":"/auth/open-id","icon":"fa fa-cubes"},
{"title":"Line","path":"/auth/line","icon":"fa fa-comments-o"},
{"title":"分析文件","path":"/auth/static-analysis","icon":"fa fa-line-chart"},
{"title":"Asciidoc","path":"/auth/asciidoc","icon":"fa fa-file-code-o"},
{"title":"TOTP","path":"/auth/authenticator","icon":"fa fa-clock-o"},
{"title":"WebRTC","path":"/auth/rtc","icon":"fa fa-comments"}
]
}
]
>
<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="sidebar-sticky pt-3">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link <#if springMacroRequestContext.requestUri=='/index'>active</#if>" href="/index">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="feather feather-home">
                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                    </svg>
                    首頁
                </a>
            </li>

            <#list menu as title>
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted collapsed active"
                    data-toggle="collapse" data-target="#${title.title}">
                    <span>${title.title}</span>

                    <a class="d-flex align-items-center text-muted " href="#">

                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="feather feather-plus-circle">
                            <circle cx="12" cy="12" r="10"></circle>
                            <line x1="12" y1="8" x2="12" y2="16"></line>
                            <line x1="8" y1="12" x2="16" y2="12"></line>
                        </svg>
                    </a>
                </h6>
                <ul class="sub-menu collapse show " id="${title.title}">
                    <#list title.item as topic>
                        <li class="nav-item">
                            <a class="nav-link  <#if springMacroRequestContext.requestUri==topic.path>active</#if>"
                               href="${topic.path}">
                                <i class="${topic.icon}"></i> ${topic.title}
                            </a>
                        </li>
                    </#list>
                </ul>
            </#list>
        </ul>
    </div>
</nav>
