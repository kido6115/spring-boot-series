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
                <h1 class="h2">Google Map API</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/resources/templates/google-map.ftl">Map</a>

                        </div>
                    </div>
                </div>
            </div>
            <p>透過google map javascript api進行功能開發, 具備以下功能<a target="_blank"
                                                                         href="<@spring.url '/auth/google-map-demo' />"
                                                                         class="btn btn-outline-primary">試用</a>
            </p>


            <h5>複合地圖</h5>
            <p>三種地圖都具備拖拉同步移動, 各自有各自的圖層進行比較</p>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">單一地圖</h5>
                    </div>
                    <p class="mb-1">整面版為單一張地圖</p>
                </a>
            </div>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">滑曳模式</h5>
                    </div>
                    <p class="mb-1">中間灰色界線進行左右拖拉, 以比較兩張地圖差異</p>
                </a>
            </div>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">雙視窗模式</h5>
                    </div>
                    <p class="mb-1">同時檢視兩張地圖</p>
                </a>
            </div>
            <br/>
            <h5>圖磚選擇</h5>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">Google 預設圖磚</li>
                <li class="list-group-item">Open Street Map圖磚</li>
                <li class="list-group-item">MapBox圖磚, 提供自行定義地圖各種顏色樣式等</li>
                <li class="list-group-item">台灣通用版電子地圖圖磚</li>
            </ul>
            <br/>
            <h5>查詢條件</h5>
            <p>空白區域, 供嵌入網頁上額外客製的內容</p>
            <br/>
            <h5>定位路線</h5>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">Google路線規劃</h5>
                    </div>
                    <p class="mb-1">透過Direction API 可以將Google的路線規劃嵌入網頁中</p>
                </a>
            </div>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">Google定位</h5>
                    </div>
                    <p class="mb-1">透過Geocoding API將地址或者地名轉為經緯度座標</p>
                </a>
            </div>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">Google多維距離矩陣</h5>
                    </div>
                    <p class="mb-1">透過Distance Matrix API可以了解複數個位置彼此間的距離, 以便於路線規劃</p>
                </a>
            </div>
            <br/>
            <h5>面積計算</h5>
            <p>利用Google map Draw Manager來與地圖互動, 可透過圈選的範圍取得相對應的地理資訊, 並且與本身資料交互使用,
                可達到圈選資料效果</p>
            <br/>
            <h5>圖層設定</h5>
            <p>自訂義圖層與一般圖磚套疊具備互動性質, 可以取得點擊的點、線、面等相關資料, 進一步達成更多功能</p>
            <div class="list-group">
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">動態資料</h5>
                    </div>
                    <p class="mb-1">可根據狀況即時演算並顯示地圖上</p>
                </a>
                <a class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h5 class="mb-1">靜態資料</h5>
                    </div>
                    <p class="mb-1">如外部KML或者WMS套疊</p>
                </a>
            </div>
        </main>
    </div>
</div>
</body>

</html>
