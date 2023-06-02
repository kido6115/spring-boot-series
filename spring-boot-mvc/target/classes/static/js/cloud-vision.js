$(function () {
    startup();

    $('#file').change(function () {
        if ($(this).val() !== '') {
            var accept = ['png', 'jpg', 'jpeg'];
            var file = $(this).val().split(/(\\|\/)/g).pop();
            if (accept.indexOf(file.split('.').pop()) > -1) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#preview').attr('src', e.target.result);
                    var text = removePrefix(e.target.result);
                    detect(text, function (data) {
                        $('#text-file').text(data.responses[0].fullTextAnnotation.text);
                    });
                }
                reader.readAsDataURL(this.files[0]);
            }
        }
    });
});
var width = 320;
var height = 0;
var streaming = false;
var video = null;
var canvas = null;
var photo = null;
let startButton = null;

function startup() {

    video = document.getElementById('video');
    canvas = document.getElementById('canvas');
    photo = document.getElementById('photo');
    startButton = document.getElementById('start-button');

    navigator.mediaDevices
        .getUserMedia({video: true, audio: false})
        .then((stream) => {
            video.srcObject = stream;
            video.play();
        })
        .catch((err) => {
            console.error(`An error occurred: ${err}`);
        });

    video.addEventListener(
        'canplay',
        (ev) => {
            if (!streaming) {
                height = video.videoHeight / (video.videoWidth / width);


                if (isNaN(height)) {
                    height = width / (4 / 3);
                }

                video.setAttribute('width', width);
                video.setAttribute('height', height);
                canvas.setAttribute('width', width);
                canvas.setAttribute('height', height);
                streaming = true;
            }
        },
        false
    );

    startButton.addEventListener(
        'click',
        (ev) => {
            snapshot();
            ev.preventDefault();
        },
        false
    );

    // clear();
}

function clear() {
    var context = canvas.getContext('2d');
    context.fillStyle = '#AAA';
    context.fillRect(0, 0, canvas.width, canvas.height);

    var data = canvas.toDataURL('image/png');
    photo.setAttribute('src', data);
}

function snapshot() {
    var context = canvas.getContext('2d');
    if (width && height) {
        canvas.width = width;
        canvas.height = height;
        context.drawImage(video, 0, 0, width, height);

        var data = canvas.toDataURL('image/png');
        photo.setAttribute('src', data);
        var text = removePrefix(data);
        detect(text, function (data) {
            $('#text').text(data.responses[0].fullTextAnnotation.text);
        });
    } else {
        clear();
    }
}

function detect(data, callBack) {
    $.post('/auth/detect', {
        para: data,
        '_csrf': $('meta[name="_csrf"]').attr('content')
    }, function (data) {
        data = JSON.parse(data);
        if (Object.keys(data.responses[0]).length !== 0) {
            callBack(data);
        }
    });
}

function removePrefix(dataUrl) {
    var regex = /^data:image\/(png|jpg|jpeg|gif);base64,/
    return dataUrl.replace(regex, "");
}
