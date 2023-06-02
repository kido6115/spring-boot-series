<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <link href="<@spring.url "/webjars/font-awesome/4.7.0/css/font-awesome.min.css" />"
          rel="stylesheet"/>

    <script
            src="https://maps.googleapis.com/maps/api/js?key=${key}&libraries=visualization,drawing,geometry"></script>
    <script src="/webjars/markerclustererplus/2.1.2/markerclusterer_packed.js"></script>
    <#--    <script src="/js/sungyeh-google-config.js"></script>-->
    <script src="/js/sungyeh-google-map.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/google-map-style.css">
    <!-- 彰化geojson -->
    <script type="text/javascript" src="/js/chcg.js"></script>
    <!-- 台中geojson -->
    <script type="text/javascript" src="/js/taichung.js"></script>
    <!-- 觀光詳細資料 -->
    <script type="text/javascript" src="/js/spot.js"></script>
    <!-- 觀光靜態經緯度資料 -->
    <script type="text/javascript" src="/js/latlng.js"></script>
    <script type="text/javascript" src="/js/samelatlng.js"></script>
    <script type="text/javascript" src="/js/map.js"></script>
    <style>
        .d-none {
            display: none !important;;
        }
    </style>
    <script type="text/javascript">

    </script>

</head>
<body>
<div id="map"></div>
<div id="map2"></div>
<div id="compare" style="width:100%;height:100%"></div>
<div id="serviceElement" class="srvcElement">
    <div class="w-100 border mb-1">
        <button class="btn text-dark card-header h5" id="headingOne" type="button" data-toggle="collapse"
                data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
            Google路線規劃
        </button>
        <div id="collapseOne" class="collapse form-group mb-0 p-3" aria-labelledby="headingOne"
             data-parent="#serviceElement">
            <label for="org"> 起點 : </label>
            <input id="org" class="form-control mb-3" value="台中市北區太原路一段530號"/>
            <label for="dest"> 終點 : </label>
            <input id="dest" class="form-control mb-3" value="台中勤美"/>
            <div class="text-center mb-1">
                <button type="button" class="btn btn-outline-success fa-search" id="directionSearch"> 查詢</button>
                <!--追加刪除按鈕 無實際功能 待補-->
                <button type="button" class="btn btn-outline-danger fa-trash" id=""> 刪除</button>
            </div>
            <div style="overflow:auto">
                <div id="directions_panel" style="height:250px;width:100%"></div>
            </div>
        </div>
    </div>
    <div class="w-100 border mb-1">
        <button class="btn text-dark card-header h5" type="button" data-toggle="collapse" data-target="#collapseTwo"
                aria-expanded="false" aria-controls="collapseTwo">
            Google定位
        </button>
        <div id="collapseTwo" class="collapse form-group mb-0 p-3" aria-labelledby="headingTwo"
             data-parent="#serviceElement">
            <label for="google-geocoding">地名、座標、地址、行政區</label>
            <input value="台中市北區太原路一段530號" class="form-control mb-3" id="google-geocoding"/>
            <div class="text-center mb-1">
                <button type="button" class="btn btn-outline-success fa-search" id="googleGeocodingSearch"> 查詢
                </button>
            </div>
        </div>
    </div>
    <div class="w-100 border mb-1">
        <button class="btn text-dark card-header h5" type="button" data-toggle="collapse" data-target="#collapseThree"
                aria-expanded="false" aria-controls="collapseThree">
            Google多維距離矩陣
        </button>
        <div id="collapseThree" class="collapse form-group mb-0 p-3" aria-labelledby="headingThree"
             data-parent="#serviceElement">
            <label>起點</label>
            <input value="台中勤美" class="matrix-start form-control mb-3"/>
            <label>起點</label>
            <input value="台中市北區太原路一段530號" class="matrix-start form-control mb-3"/>
            <label>迄點</label>
            <input value="台中科博館" class="matrix-target form-control mb-3"/>
            <label>迄點</label>
            <input value="台中火車站" class="matrix-target form-control mb-3"/>
            <label>迄點</label>
            <input value="台中市政府" class="matrix-target form-control mb-3"/>
            <div class="text-center mb-1">
                <button type="button" id="googleMatrixSearch" class="btn btn-outline-success fa-search">查詢</button>
            </div>
            Console.log結果
        </div>
    </div>
</div>
<div id="drawElement">
    <label for="markerRadius">marker半徑:</label><input id="markerRadius">公尺<br>
    <label for="area">面積:</label><input class="infos" id="area">平方公尺<br>
    <label for="length">長度:</label><input class="infos" id="length">公尺<br>
    <label for="radius">半徑:</label><input class="infos" id="radius">公尺<br>
    <label for="latLng">座標:</label><input class="infos" id="latLng"><br>
    <label for="bound">邊界:</label><textarea class="infos" id="bound" rows="4" cols="30"></textarea>
    <label for="dataCount">範圍內資料數:</label><input class="infos" id="dataCount"><br>
</div>
</body>

</html>
