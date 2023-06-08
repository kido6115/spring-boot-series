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
                <h1 class="h2">AsciiDoc</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/tree/master/doc/asciidoc">
                                Ascidoc </a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/tree/master/doc/asciidoc/flow">
                                PlantUML </a>
                        </div>
                    </div>
                </div>
            </div>
            <h5>Why Asciidoc</h5>
            <p> 相較市面上常用的Markdown, Asciidoc有更多功能, 更多的套件可以使用, 此外有官方的支援, 各式的IDE支援,
                產出的格式相對多樣, 如:HTML, PDF...等
            </p>
            <p>
                Asciidoc支援整合PlantUML, 透過標記語言繪製各式UML, 讓Asciidoc不僅僅只是標記語言產出技術文件, 技術規格書,
                報告書...等都是非常好的選擇
            </p>
            <p>
                由於是標記語言相對於Word, PPT等文件格式, 有著更好的版本控制, 在資安以及統整文件上都有著更好的優勢,
                且支援Maven及Gradle也能做到一定程度自動化產出
            </p>
            <br/>
            <h5>Who’s using AsciiDoc</h5>
            <div class="list-group">
                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h6 class="mb-1"> O’Reilly</h6>
                    </div>
                </a>
            </div>
            <div class="list-group">
                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h6 class="mb-1"> GitHub</h6>
                    </div>
                    <p class="mb-1">除了README.md, README.adoc已可以再Github進行預覽</p>

                </a>
            </div>
            <div class="list-group">
                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h6 class="mb-1"> Hibernate</h6>
                    </div>
                </a>
            </div>
            <div class="list-group">
                <a href="#" class="list-group-item list-group-item-action flex-column align-items-start ">
                    <div class="d-flex w-100 justify-content-between">
                        <h6 class="mb-1"> Spring Framework</h6>
                    </div>
                </a>
            </div>
            <br/>
            <h5>範例</h5>
            <div class="card" style="width: 50rem;">
                <div class="card-body">
                    <p class="card-text">透過原始碼生成的簡易規格書, 其中可以將puml產生圖片並嵌入</p>
                    <a target="_blank"
                       href="https://drive.google.com/drive/folders/1oPArC63bSChcnbkAYtl5ZNVPdcCfzRoB?usp=sharing"
                       class="card-link">產生結果</a>
                </div>
            </div>
        </main>
    </div>
</div>
</body>

</html>
