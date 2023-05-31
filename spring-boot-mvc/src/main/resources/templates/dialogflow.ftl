<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <style>
        df-messenger {
            --df-messenger-button-titlebar-color: #6DB33F;
            --df-messenger-send-icon: #6DB33F;
        }
    </style>
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
                <h1 class="h2">Dialogflow</h1>
                <div class="btn-toolbar mb-2 mb-md-0">
                    <div class="dropdown">
                        <a class="btn btn-sm btn-outline-secondary dropdown-toggle" href="#" role="button"
                           id="dropdownMenuLink"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Dropdown link
                        </a>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <a class="dropdown-item" href="#">Something else here</a>
                        </div>
                    </div>
                </div>
            </div>
            <p>使用Dialogflow平台製作簡易任務型機器人, 透過平台NLP處理來找出意圖, 並回答定義內容或者Webhook :
            </p>
            <h5>意圖判斷 : 可自定義關鍵字、同義詞, 有NLP處理具一定程度處理錯別字以及文法問題</h5>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">會哪些技術/語言/框架...等</h5>
                    </div>
                    <p class="mb-1">目前具備的技術能力</p>
                </a>
            </div>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">擅長的領域</h5>
                    </div>
                    <p class="mb-1">過去曾經接觸過的領域</p>
                </a>
            </div>
            <h5>線性對話 : 透過傳遞context保存對話收集的數據達到完成一系列的對話收集</h5>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">我要訂房</h5>
                    </div>
                    <p class="mb-1">示範用的訂房機器人, 可收集預定日期、時間、人數, 透過訓練可判斷多種格式及文法 :</p>
                    <p class="mb-1">日期: 2020-01-01、2020/01/03、這個月5號</p>
                    <p class="mb-1">時間: 早上八點、14:00、2:00pm</p>
                    <p class="mb-1">人數: 3、四人、5人</p>

                </a>
            </div>
            <h5>Webhook</h5>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1"></h5>
                    </div>
                    <p class="mb-1">可將對話的內容hook到API再進行處理,
                        如 : "我要訂房"的對話結束後hook的API會建立一筆資料</p>
                </a>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>訂單編號</th>
                        <th>預定日期</th>
                        <th>人數</th>
                        <th>建立時間</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if list??>
                        <#list list as book>
                            <tr>
                                <td>${book.id}</td>
                                <td>${book.dateTime}</td>
                                <td>${book.people}</td>
                                <td>${book.createTime}</td>

                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
            <df-messenger
                    intent="WELCOME"
                    chat-title="忍不助"
                    agent-id="3cbcf58f-5f4e-4db8-add0-54c0ca2b374e"
                    language-code="zh-tw"
                    chat-icon="/img/spruce-3-removebg-preview.png"
            ></df-messenger>
        </main>
    </div>
</div>
</body>

</html>
