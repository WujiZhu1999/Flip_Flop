var flipFlopClient;
var roomKey;
var localBoardVersion = 0;
var needFetchCurrentGame = false;


function connectToRoom(){
    var socket = new SockJS('/flipFlopSocket01');
    flipFlopClient = Stomp.over(socket);
    flipFlopClient.connect({}, function (frame){
        flipFlopClient.subscribe("/flipflop/"+roomKey, function (response){

            var responseBody = JSON.parse(response.body);
            var version = responseBody.boardObject.version;

            /**
             * The idea is since the system is highly asynchronous,players may choose to:
             * 1. Click the board anytime which will trigger click and maybe cause the board to broadcast in socket
             * 2. Click start new game which will cause board to broadcast in socket with new board information
             *
             * While socket is tcp which is in sequence, but request may come to server in various time which may cause click
             * command from last game arrive server even after a new game.
             *
             * So use board version number to control synchronous behavior.
             *
             * If current board number is lower than the provided version, means this is a new game, load the whole new board
             * If current board number is same as the provided version, means on same game, check if there is update, if update required, update
             * If current board number is higher than the provided version, means this is due to wrong/delayed broadcast from server ignore
             *
             * */

            if(version > localBoardVersion) {
                loadBoard(responseBody.boardObject);
            }

            console.log(responseBody);
            console.log("here");
        });
        if(needFetchCurrentGame){
            needFetchCurrentGame = false;
            fetchGame();
        }
    });

}

function fetchRoomKey(){
    roomKey = $("#roomKey").text();
}

function startNewGame(){

    flipFlopClient.send("/flipflop/newGame/"+roomKey, {}, {});
}

function askToFetchGame(){
    needFetchCurrentGame = true;
}

function fetchGame(){
    flipFlopClient.send("/flipflop/currentGame/"+roomKey, {}, {});
}

function loadBoard(boardObject){
    localBoardVersion = localBoardVersion + 1;
    $("#boardTable").empty();

    var table = "<table>";
    for (row of boardObject.cells){
        table = table.concat("<tr>");
        for(cell of row){
            table = table.concat("<td>");
            table = table.concat(getImageString(cell));
            table = table.concat("</td>");
        }
        table = table.concat("</tr>");
    }
    table.concat("</table>")

    $("#boardTable").append(table);

}

function getImageString(cell){
    var out = "<img id=\"" + cell.x + cell.y + "\" src= \"" + cell.image.path + "\" />"
    return out;
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