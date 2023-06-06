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
                <h1 class="h2">JMeter</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/java/com/sungyeh/web/AuthenticationController.java">Controller</a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/resources/templates/mvc.ftl">Freemarker</a>
                        </div>
                    </div>
                </div>
            </div>
            <h5>GUI</h5>
            <p>JMeter有提供GUI撰寫壓測計畫, 但是操作上較為繁瑣所以建議安裝Chrome擴充工具BlazeMeter,
                透過瀏覽器操作來錄製JMeter的壓測計畫
            </p>
            <h5>BlazeMeter</h5>
            <p>安裝好Plugin後免費註冊後登入, BlazeMeter為持續測試平台, 可付費使用,
                但我們僅需要他幫我們匯出JMeter格式(jmx)的壓測計畫</p>
            <div class="row">
                <img class=" col-md-6" src="/img/jmeter-1.png">
                <img class=" col-md-5" src="/img/jmeter-2.png">
            </div>
            <br/>
            <p>將錄製好的腳本使用JMeter GUI開啟再進行調整, BlazeMeter錄製的腳本相較原生提供的方式較為簡潔且重點,
                但仍有部分需要自行調整</p>
            <p>可以注意到BlazeMeter已經自動過濾靜態資源, 但如csrf token是回應後才產生的,
                所以我們可以透過在 GET-/login 新增後置處理器進行擷取, 透過各式處理器進行參數調整</p>
            <div class="row">
                <img class="img-fluid col-md-8" src="/img/jmeter-3.png">
            </div>
            <br/>
            <p>透過後置CSS處理器擷取csrf token, 為方便壓測暫時不使用captcha驗證</p>
            <div class="row">
                <img class="img-fluid col-md-6" src="/img/jmeter-4.png">
                <img class="img-fluid col-md-6" src="/img/jmeter-5.png">
            </div>
            <br/>
            <p>新增Linstener以獲得結果報告</p>
            <div class="row">
                <img class="img-fluid col-md-6" src="/img/jmeter-6.png">
                <img class="img-fluid col-md-3" src="/img/jmeter-7.png">
            </div>
            <br/>
            <h5>CLI</h5>
            <p>GUI工具目的為調校壓測計畫, 實際上進行壓力測試時仍建議使用CLI, 透過指令執行JMeter
            </p>
            <pre>
                 <code data-language="shell">
                jmeter -n -t &lt;test JMX file&gt; -l &lt;test log file&gt; -e -o &lt;Path to output folder&gt;
                 </code>
            </pre>
            <div class="row">
                <img class="img-fluid col-md-10" src="/img/jmeter-8.png">
            </div>
            <br/>
            <h5>Jenkins</h5>
            <p>透過Jenkins觸發壓力測試, 設定threshold來進行監控, e.g. 新功能上線於離峰時間進行壓測,
                若發現低於標準能主動警示</p>
            <h5>Codegen</h5>
            <p>使用Codegen透過OAS可以自動化產生部分jmx</p>
        </main>
    </div>
</div>
</body>

</html>
