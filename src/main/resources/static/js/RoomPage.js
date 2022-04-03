var flipFlopClient;

function connectToRoom(){
    alert("aaab");
    var socket = new SockJS('/flipFlopSocket01');
    flipFlopClient = Stomp.over(socket);
    flipFlopClient.connect({}, function (frame){
        flipFlopClient.subscribe("/flipflop/room1", function (board){
            console.log(board);
            console.log("here");
        });
    });

}

function startNewGame(){
    flipFlopClient.send("/flipflop/newGame", {}, {});
}

$(document).ready( function(){
    connectToRoom();
});

$(function (){
    $("#getNewGameButton").click(function (){
        startNewGame();
    });
});