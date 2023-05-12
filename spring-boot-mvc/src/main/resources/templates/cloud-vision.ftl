<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "meta.ftl" />
    <script src="/js/cloud-vision.js"></script>
    <style>
        #preview {
            height: 30vh;
        }
    </style>
</head>
<body>
<#include "nav.ftl" />

<div class="container-fluid">
    <div class="row">
        <#include "sidebar.ftl" />

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
                <h1 class="h2">CloudVision</h1>
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
