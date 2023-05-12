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
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">TOTP Authenticator</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="btn-group mr-2">
                        <button type="button" class="btn btn-sm btn-outline-secondary">Share</button>
                        <button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
                    </div>
                    <button type="button" class="btn btn-sm btn-outline-secondary dropdown-toggle">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="feather feather-calendar">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                            <line x1="16" y1="2" x2="16" y2="6"></line>
                            <line x1="8" y1="2" x2="8" y2="6"></line>
                            <line x1="3" y1="10" x2="21" y2="10"></line>
                        </svg>
                        This week
                    </button>
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
