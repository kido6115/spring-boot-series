<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script>
        $(function () {
            var urlParams = new URLSearchParams(window.location.search);
            if (urlParams.has('code')) {
                var code = urlParams.get('code');
                $.ajax({
                    url: '/oauth2/token',
                    type: 'POST',
                    headers: {
                        Authorization: 'Basic Y2xpZW50OnBhc3N3b3Jk'
                    },
                    data: {
                        code: code,
                        grant_type: 'authorization_code',
                        redirect_uri: 'http://127.0.0.1:8080/index'
                    },
                    success: function (data) {
                        var ul = $('<ul></ul>');
                        ul.append('<li>access_token: ' + data.access_token + '</li>');
                        ul.append('<li>token_type: ' + data.token_type + '</li>');
                        ul.append('<li>expires_in: ' + data.expires_in + '</li>');
                        ul.append('<li>refresh_token: ' + data.refresh_token + '</li>');
                        $('.row').append(ul);
                    }
                });

            }
        });
    </script>
</head>
<body>

<div class="container-fluid">
    <div class="row">

    </div>
</div>
</body>

</html>
