<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script src="/js/cloud-vision.js"></script>
    <style>
        #preview {
            height: 30vh;
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
                <h1 class="h2">CloudVision</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/tree/master/cloud-vision-api">
                                Cloud Vision 相關</a>
                        </div>
                    </div>
                </div>
            </div>
            <h2>視訊鏡頭圖片文字辨識</h2>
            <div class="content-area row">
                <div class="camera col-md-4">
                    <video id="video">Video stream not available.</video>
                    <button class="btn btn-danger" id="start-button">擷取畫面</button>
                </div>
                <canvas hidden id="canvas"></canvas>
                <div class="output col-md-4">
                    <img id="photo"/>
                </div>

                <textarea id="text" rows="5" cols="33" class="col-md-4">
                </textarea>
            </div>
            <h2>上傳圖片文字辨識</h2>
            <div class="row">
                <div class="col-md-4">
                    <p>圖片請勿超過75M pixels</p>
                    <input id="file" type="file"/>
                </div>
                <div class="output col-md-4">
                    <img id="preview"/>
                </div>
                <textarea id="text-file" rows="5" cols="33" class="col-md-4">
                </textarea>
            </div>
        </main>
    </div>
</div>
</body>

</html>
