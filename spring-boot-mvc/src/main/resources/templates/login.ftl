<#ftl output_format="HTML" />
<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script src="https://www.google.com/recaptcha/api.js"></script>
    <script src="https://accounts.google.com/gsi/client" async defer></script>

    <style>
        /* Coded with love by Mutiullah Samim */
        body,
        html {
            background: #e2e3e5;
            margin: 0;
            padding: 0;
            height: 100%;
        }

        .user_card {
            height: 400px;
            width: 350px;
            margin-top: auto;
            margin-bottom: auto;
            background: #ffffff;
            position: relative;
            display: flex;
            justify-content: center;
            flex-direction: column;
            padding: 10px;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            -webkit-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            -moz-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            border-radius: 5px;

        }

        .brand_logo_container {
            position: absolute;
            height: 170px;
            width: 170px;
            top: -75px;
            border-radius: 50%;
            padding: 10px;
            text-align: center;
        }

        .brand_logo {
            height: 150px;
            width: 150px;
            border-radius: 50%;
            border: 2px solid white;
        }

        .form_container {
            margin-top: 100px;
        }

        .login_btn {
            width: 100%;
            background: #c0392b !important;
            color: white !important;
        }

        .login_btn:focus {
            box-shadow: none !important;
            outline: 0px !important;
        }

        .login_container {
            padding: 0 2rem;
        }

        .input-group-text {
            background: #17a2b8 !important;
            color: white !important;
            border: 0 !important;
            border-radius: 0.25rem 0 0 0.25rem !important;
        }

        .input_user,
        .input_pass:focus {
            box-shadow: none !important;
            outline: 0px !important;
        }

        .custom-checkbox .custom-control-input:checked ~ .custom-control-label::before {
            background-color: #c0392b !important;
        }
    </style>
    <script>
        function callback(a) {
            $('#sso-credential').val(a.credential);
            $('#sso-form').submit();
        }

        $(function () {
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    var latitude = position.coords.latitude;
                    var longitude = position.coords.longitude;
                    $('#lat,#sso-lat').val(latitude);
                    $('#lng,#sso-lng').val(longitude);

                },
                function (error) {
                    console.log(error.message);
                }
            );

        });
    </script>
</head>
<div class="container h-100">
    <div class="d-flex justify-content-center h-100">
        <div class="user_card">
            <div class="d-flex justify-content-center">
                <div class="brand_logo_container">
                    <img src="<@spring.url '/img/spruce-2.png' />"
                         class="brand_logo" alt="Logo">
                </div>
            </div>
            <div class="d-flex justify-content-center form_container">
                <form method="post" action="/login/process">
                    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>

                    <div class="input-group mb-3">
                        <div class="input-group-append">
                            <span class="input-group-text"><i class="fa fa-user" aria-hidden="true"></i></span>
                        </div>
                        <input type="text" id="username" name="username" class="form-control input_user" value=""
                               placeholder="username">
                    </div>
                    <div class="input-group mb-2">
                        <div class="input-group-append">
                            <span class="input-group-text"><i class="fa fa-lock" aria-hidden="true"></i></span>
                        </div>
                        <input type="password" id="password" name="password" class="form-control input_pass" value=""
                               placeholder="password">
                        <input type="hidden" id="lat" name="lat"/>
                        <input type="hidden" id="lng" name="lng"/>
                    </div>
                    <div class="form-group">
                        <div id="recaptcha" class="g-recaptcha" data-sitekey="${site}"></div>
                    </div>

                    <div class="d-flex justify-content-center mt-3 login_container">
                        <button name="button" class="btn btn-info">Sign in</button>
                    </div>
                    <div class="form-group">
                        <div id="g_id_onload"
                             data-client_id="${clientId}"
                             data-context="signin"
                             data-ux_mode="popup"
                             data-callback="callback"
                             data-auto_prompt="false">
                        </div>
                        <div class="g_id_signin"
                             data-type="standard"
                             data-shape="rectangular"
                             data-theme="outline"
                             data-text="signin_with"
                             data-size="large"
                             data-logo_alignment="left">
                        </div>
                    </div>
                </form>
                <form id="sso-form" method="post" action="/google/sso">
                    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                    <input hidden id="sso-credential" name="credential"/>
                    <input hidden id="sso-lat" name="lat"/>
                    <input hidden id="sso-lng" name="lng"/>

                </form>
            </div>
        </div>
    </div>
</div>
</html>
