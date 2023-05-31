<#ftl output_format="HTML" />
<#assign menu=[
{
"title":"Spring",
"item":[
{"title":"MVC","path":"/auth/mvc"},
{"title":"JPA","path":"/auth/jpa"},
{"title":"Security","path":"/auth/security"},
{"title":"Authorization/Resource Server","path":"/auth/authorization"},
{"title":"Restful/Swagger/SpringDoc","path":"/auth/swagger"},
{"title":"WebSocket","path":"/auth/web-socket"}
]
},
{
"title":"Google",
"item":
[
{"title":"Goolge Map API","path":"/auth/google-map"},
{"title":"Cloud Vision API","path":"/auth/cloud-vision"},
{"title":"Dialogflow","path":"/auth/dialogflow"},
{"title":"Recaptcha API","path":"/auth/recaptcha"}
]
},
{
"title":"Other",
"item":
[
{"title":"OpenID","path":"/auth/open-id"},
{"title":"Line","path":"/auth/line"},
{"title":"分析文件","path":"/auth/static-analysis"},
{"title":"Asciidoc","path":"/auth/asciidoc"},
{"title":"TOTP","path":"/auth/authenticator"},
{"title":"WebRTC","path":"/auth/rtc"}
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
                            <a class="nav-link <#if springMacroRequestContext.requestUri==topic.path>active</#if>"
                               href="${topic.path}">
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                     fill="none"
                                     stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                     stroke-linejoin="round"
                                     class="feather feather-users">
                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                                    <circle cx="9" cy="7" r="4"></circle>
                                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                                    <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                                </svg>
                                ${topic.title}
                            </a>
                        </li>
                    </#list>
                </ul>
            </#list>
        </ul>
    </div>
</nav>
