<#ftl output_format="HTML" />
<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "meta.ftl" />
    <script src="https://www.google.com/recaptcha/api.js"></script>

</head>
<form method="post" action="/login/process">
    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>

    <label for="username">Username</label>
    <input id="username" name="username">
    <label for="password">Password</label>
    <input id="password" name="password" type="password">
    <div class="g-recaptcha" data-sitekey="${site}"></div>
    <button>送出</button>
</form>
</html>
