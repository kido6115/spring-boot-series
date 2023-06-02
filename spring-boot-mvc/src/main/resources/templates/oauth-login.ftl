<#ftl output_format="HTML" />
<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />

</head>
<form method="post" action="/oauth2/login/process">
    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>

    <label for="username">Username</label>
    <input id="username" name="username">
    <label for="password">Password</label>
    <input id="password" name="password" type="password">
    <button>送出</button>
</form>
</html>
