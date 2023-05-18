<#ftl output_format="HTML" />

<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">

    <a class="navbar-brand col-md-3 col-lg-2 mr-0 px-3" href="#"><img style="height: 40px"
                                                                      src="/img/spruce-3-removebg-preview.png"/>
        &nbsp; &nbsp;
        Sungyeh Tech Note
    </a>
    <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-toggle="collapse"
            data-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <#if SPRING_SECURITY_CONTEXT??>
                <form method="post" action="/logout">
                    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                    <button class="btn btn-primary">Sign
                        out
                    </button>
                </form>
            <#else>
                <form method="get" action="/login">
                    <button class="btn btn-primary">Sign in</button>
                </form>
            </#if>

        </li>
    </ul>
</nav>
