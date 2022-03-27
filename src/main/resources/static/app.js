var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function clicking() {
    stompClient.send("/app/click", {}, JSON.stringify({'w': parseInt($("#w").val()),'h': parseInt($("#h").val())}));
}

var ffClient = null;

function ffConnect() {
    var socket = new SockJS('/ff2-socket');
    ffClient = Stomp.over(socket);
    ffClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        ffClient.subscribe('/ffGame/room1', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function ffStartBoard() {
    ffClient.send("/ffApp/start", {}, JSON.stringify({'name': $("#name").val()}));
}

function ffFlipBoard() {
    ffClient.send("/ffApp/flip", {}, JSON.stringify({'w': parseInt($("#w").val()),'h': parseInt($("#h").val())}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#click").click(function (){clicking(); });
    $( "#ffStart").click(function () {
        ffStartBoard();
    });

    $( "#ffFlip").click(function () {
        ffFlipBoard();
    });
    $( "#ffConnect").click(function (){
        ffConnect();
    });
});