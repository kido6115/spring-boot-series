$(function () {
    var socket = new SockJS('/text-ccs');
    var stompClient = Stomp.over(socket);

    stompClient.connect({user: 'user1'}, function (frame) {
        room = frame.headers['user-name'];
        console.log('Connected: ' + frame.headers['user-name']);


        stompClient.subscribe('/text-ccs/user/' + frame.headers['user-name'] + '/subscribe', function (messageOutput) {
            $('#user1-text').append(
                JSON.parse(messageOutput.body).message.from + ' : ' +
                JSON.parse(messageOutput.body).message.text + '\n');
        });

        $('#user1-button').click(function () {
            $.ajax({
                url: '/api/text-ccs/send/' + 'user2',
                data: JSON.stringify({
                    "from": 'user1',
                    "text": $('#user1-name').val()
                }),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=utf-8",
                success: function (returnData) {
                    $('#user1-name').val('');
                    $('#user1-text').append(
                        returnData.message.from + ' : ' +
                        returnData.message.text + '\n');

                }
            });
        });
    });

    var socket2 = new SockJS('/text-ccs');
    var stompClient2 = Stomp.over(socket2);

    stompClient2.connect({user: 'user2'}, function (frame) {
        console.log('Connected: ' + frame.headers['user-name']);


        stompClient2.subscribe('/text-ccs/user/' + frame.headers['user-name'] + '/subscribe', function (messageOutput) {
            $('#user2-text').append(
                JSON.parse(messageOutput.body).message.from + ' : ' +
                JSON.parse(messageOutput.body).message.text + '\n');
        });

        $('#user2-button').click(function () {
            $.ajax({
                url: '/api/text-ccs/send/' + 'user1',
                data: JSON.stringify({
                    "from": 'user2',
                    "text": $('#user2-name').val()
                }),
                type: "POST",
                dataType: "json",
                contentType: "application/json;charset=utf-8",
                success: function (returnData) {
                    $('#user2-name').val('');
                    $('#user2-text').append(
                        returnData.message.from + ' : ' +
                        returnData.message.text + '\n');
                }
            });
        });
    });
    $('#user3-button').click(function () {
        $.ajax({
            url: '/api/text-ccs/broadcast',
            data: JSON.stringify({
                "from": 'admin',
                "text": $('#user3-name').val()
            }),
            type: "POST",
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function (returnData) {
                $('#user3-name').val('');
                $('#user1-text').append(
                    returnData.message.from + ' : ' +
                    returnData.message.text + '\n');
                $('#user2-text').append(
                    returnData.message.from + ' : ' +
                    returnData.message.text + '\n');
                $('#user3-text').append(
                    returnData.message.from + ' : ' +
                    returnData.message.text + '\n');
            }
        });
    });
});
