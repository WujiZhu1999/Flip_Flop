var flipFlopClient;
var roomKey;
function connectToRoom(){
    var socket = new SockJS('/flipFlopSocket01');
    flipFlopClient = Stomp.over(socket);
    flipFlopClient.connect({}, function (frame){
        flipFlopClient.subscribe("/flipflop/room1", function (board){
            console.log(board);
            console.log("here");
        });
    });

}

function fetchRoomKey(){
    roomKey = $("#roomKey").text();
}

function startNewGame(){

    $.ajax({
        type: "get",
        url: "/newGame/111",
        cache: false,
        success: function(response) {
            console.log(response);
        }
    });
}

$(document).ready( function(){
    fetchRoomKey();
    connectToRoom();


});

$(function (){
    $("#getNewGameButton").click(function (){
        startNewGame();
    });
});