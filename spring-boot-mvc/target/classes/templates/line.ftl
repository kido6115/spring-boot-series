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
            <div class="d-flex  flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Line</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/tree/master/line">
                                Line相關 </a>
                        </div>
                    </div>
                </div>
            </div>
            <h5>Line bot</h5>
            <p>透過Line Messaging API 來實作Line bot進行訊息的收發
            </p>
            <p>使用Line掃描以下QR code進行體驗
            </p>
            <div class="row">
                <div class="col-md-6">
                    <h5>指令 </h5>
                    <div class="list-group">
                        <a href="#ask" class="list-group-item list-group-item-action flex-column align-items-start ">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">!你會甚麼</h5>
                            </div>
                            <p class="mb-1">基本功能說明</p>
                        </a>
                    </div>
                    <div class="list-group">
                        <a href="#cwb" class="list-group-item list-group-item-action flex-column align-items-start ">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">!天氣</h5>
                            </div>
                            <p class="mb-1">透過網頁爬蟲將氣象局首頁資訊透過Line bot回應</p>
                        </a>
                    </div>
                    <h5>排程 </h5>
                    <div class="list-group">
                        <a href="#dokodemo"
                           class="list-group-item list-group-item-action flex-column align-items-start ">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">排程推播</h5>
                            </div>
                            <p class="mb-1">透過GCP Cloud scheduler排程, 自動定時去取得某代購網站當日特賣商品訊息</p>
                        </a>
                    </div>
                </div>
                <div class="col-md-3">
                    <img class="img-fluid" src="/img/612libxu.png"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h5><span class="badge badge-light" id="ask">!你會甚麼</span></h5>
                    <img class="img-fluid " src="/img/1685341398391.jpg"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h5><span class="badge badge-light" id="cwb">!天氣</span></h5>
                    <img class="img-fluid " src="/img/1685338997236.jpg"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h5><span class="badge badge-light" id="dokodemo">排程推播</span></h5>
                    <img class="img-fluid" src="/img/1685339038707.jpg"/>
                </div>
            </div>
        </main>
    </div>
</div>
</body>

</html>
