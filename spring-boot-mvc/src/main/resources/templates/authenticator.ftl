<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <style>
        .img {
            height: 60vh;
        }
    </style>
    <script>
        $(function () {
            $('.badge').hide();
            $('#totp-check').click(function () {
                if ($('#totp').val()) {
                    $.getJSON('/auth/totp-check/' + $('#totp').val(), function (data) {
                        if (data) {
                            $('.badge-success').show();
                            $('.badge-danger').hide();
                        } else {
                            $('.badge-success').hide();
                            $('.badge-danger').show();
                        }
                    });
                }
            });
        });
    </script>
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
                <h1 class="h2">TOTP Authenticator</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/tree/master/totp-authenticator">
                                TOTP相關 </a>
                        </div>
                    </div>
                </div>
            </div>
            <h2>下載 iOS/Android Google Authenticator</h2>
            <p>選擇掃描QR圖碼, 並掃描右方QRcode, 於下方驗證sungyeh-tech-note下方數字是否有效</p>
            <div class="row">
                <div class="col-md-3">
                    <img class="img" src="/img/S__27582471.jpg"/>
                </div>
                <div class="col-md-3">
                    <img class="img" src="/img/S__27582469.jpg"/>
                </div>
                <div class="col-md-3">
                    <img class="img" src="${qrcode}"/>
                </div>
            </div>
            <h2>Google Authenticator 驗證</h2>
            <div class="row">
                <label for="totp">
                    請輸入Google Authenticator APP中6位數字驗證碼 :
                </label>
                <input id="totp">
                <button id="totp-check" class="btn btn-primary" type="button">驗證</button>
                <span class="badge  badge-success">驗證成功</span>
                <span class="badge  badge-danger">驗證失敗</span>
            </div>
        </main>
    </div>
</div>
</body>

</html>
