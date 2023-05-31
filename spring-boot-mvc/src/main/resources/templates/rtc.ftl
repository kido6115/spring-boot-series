<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script src='https://meet.jit.si/external_api.js'></script>
    <script>
        $(function () {
            var tools =
                [
                    'camera',
                    'chat',
                    'closedcaptions',
                    'desktop',
                    'download',
                    'embedmeeting',
                    'etherpad',
                    'feedback',
                    'filmstrip',
                    'fullscreen',
                    'hangup',
                    'help',
                    'livestreaming',
                    'microphone',
                    'mute-everyone',
                    'mute-video-everyone',
                    'participants-pane',
                    'profile',
                    'raisehand',
                    'recording',
                    'security',
                    'select-background',
                    'settings',
                    'shareaudio',
                    'sharedvideo',
                    'shortcuts',
                    'stats',
                    'tileview',
                    'toggle-camera',
                    'videoquality',
                    'invite',
                    '__end'
                ];
            api = new JitsiMeetExternalAPI("meet.jit.si", {
                roomName: "sungyeh-tech-note",
                parentNode: document.querySelector('#jaas-container'),
                height: '500px',
                configOverwrite: {
                    toolbarButtons: tools,
                    defaultLanguage: 'zhTW',
                    readOnlyName: false,
                    prejoinConfig: {
                        enabled: true,
                    },

                    disableReactionsModeration: true,
                    participantsPane: {
                        hideModeratorSettingsTab: true,
                        hideMoreActionsButton: true,
                        hideMuteAllButton: true
                    }
                    , disableModeratorIndicator: true
                }
            });
            api.addListener('readyToClose', function () {
                window.location.assign('/auth/rtc');
            });


        });
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
            <div class="d-flex  flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">WebRTC</h1>
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
                               href="https://github.com/kido6115/spring-boot-series/blob/master/spring-boot-mvc/src/main/resources/templates/rtc.ftl">
                                嵌入範例 </a>
                        </div>
                    </div>
                </div>
            </div>
            <p>使用Jitsi遷入搭建WebRTC服務, jitsi有提供映像檔可自架來使用, 以下使用官方提供的server進行測試</p>
            <div id="jaas-container"></div>

        </main>
    </div>
</div>
</body>

</html>
