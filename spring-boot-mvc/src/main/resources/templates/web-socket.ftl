<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script src="/js/web-socket.js"></script>

    <script src="/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script>
    </script>
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
                <h1 class="h2">WebSocket</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/tree/master/web-socket/src/main/java/com/sungyeh">WebSocket
                                Server設定</a>
                            <a class="dropdown-item"
                               target="_blank"
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/resources/static/js/web-socket.js">
                                Client 端JS
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <h5>Spring Websocket</h5>
            <p>使用Spring Websocket實作一對一聊天以及廣播</p>
            <div class="row">
                <div class="col-md-4">
                    <div class="alert alert-info" role="alert">
                        User1
                    </div>
                    <textarea class="form-control" readonly rows="5" cols="33" id="user1-text"></textarea>
                    <div class="input-group mb-3">
                        <input id="user1-name" type="text" class="form-control">
                        <div class="input-group-append">
                            <button class="btn btn-outline-primary" id="user1-button"><i class="fa fa-paper-plane-o"
                                                                                         aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="alert alert-info" role="alert">
                        User2
                    </div>
                    <textarea class="form-control" readonly rows="5" cols="33" id="user2-text"></textarea>
                    <div class="input-group mb-3">
                        <input id="user2-name" type="text" class="form-control">
                        <div class="input-group-append">
                            <button class="btn btn-outline-primary" id="user2-button"><i class="fa fa-paper-plane-o"
                                                                                         aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="alert alert-info" role="alert">
                        廣播
                    </div>
                    <textarea class="form-control" readonly rows="5" cols="33" id="user3-text"></textarea>
                    <div class="input-group mb-3">
                        <input id="user3-name" type="text" class="form-control">
                        <div class="input-group-append">
                            <button class="btn btn-outline-primary" id="user3-button"><i class="fa fa-paper-plane-o"
                                                                                         aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <h5>MessageQueue</h5>
            <p>若讓一台AP擔任Socket收發有過載問題, 可以將訊息收取的部分轉由MQ負責, 在由AP訂閱MQ進行訊息的拉取,
                訊息由MQ承擔也可降低DB負擔並可在未有socket session建立前先保留訊息
            </p>
            <p>
                此外MQ具備順序性及簽收特質, 能卻保訊息的順序及確保訊息不會遺失, 也能做到訊息保留或者進行Event Sourcing
            </p>
            <p>
                在面對大量流入的Request MQ會有較好的表現, 且能分攤伺服器壓力, 參考以下架構</p>
            <br/>
            <h6>架構圖</h6><a href="/auth/asciidoc" class="badge badge-info">PlantUML產製</a>
            <div class="row">
                <img class="col-md-6" src="/img/websocket-mq.png"/>
            </div>
            <br/>

            <h6>流程圖</h6><a href="/auth/asciidoc" class="badge badge-info">PlantUML產製</a>
            <div class="row">
                <img class="col-md-6" src="/img/websocket-mq-flow.png"/>
            </div>
        </main>

    </div>
</div>
</body>

</html>
