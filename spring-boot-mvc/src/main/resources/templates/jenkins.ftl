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
            <div class="d-flex flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Jenkins</h1>
                <div class="btn-toolbar mb-2 mb-md-0">

                </div>
            </div>
            <p>老牌CICD工具, 除支援中文也有眾多Plugin協助使用, 並且使用Java開發, 提供自行開發Plugin工具客製化使用</p>

            <a target="_blank" class="btn btn-outline-primary"
               href="http://104.198.166.226:8080">Jenkins 使用 user/1111 登入</a>
            <h5>CI</h5>
            <p>與Maven結合使用靜態分析工具達到CI之功能, 自動化建置, 測試並產生分析文件, 透過Warnign-Next-Generation
                Plugin可將分析結果以圖表呈現
            </p>
            <p>並能快速發現問題進行處理以及追蹤, 並支援多種通知工具以及配合各類Issue tracking工具使用
            </p>
            <div class="row">
                <img class="col-md-6" src="/img/jenkins-1.png"/>
                <img class="col-md-6" src="/img/jenkins-2.png"/>
                <img class="col-md-6" src="/img/jenkins-3.png"/>
                <img class="col-md-6" src="/img/jenkins-4.png"/>

            </div>
            <br/>
            <h5>CD</h5>
            <p>參考以下流程, 持續部署可以自動化部署流程, 降低人力成本以及提升效率, 根據情況進行更多的配置</p>
            <p>如: 部署失敗策略, 主動通知...等</p>
            <a href="/auth/asciidoc" class="badge badge-info">PlantUML產製</a>

            <div class="row">
                <img class="img-fluid" src="/img/cicd-flow.png"/>

            </div>

        </main>
    </div>
</div>
</body>

</html>
