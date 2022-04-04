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
            } else if (version == localBoardVersion){

                if (responseBody.boardUpdateObject !== null){
                    updateBoard(responseBody.boardUpdateObject);
                }
            } else {
                //synchronisation error
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
    closeDropDown();
    var difficulty = $("#newGameDifficulty").find(":selected").val();
    if(difficulty.length === 0) {
        alert("Select game difficulty.")
    } else {
        flipFlopClient.send("/flipflop/newGame/"+roomKey+"/"+difficulty, {}, {});
    }
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

    var table = "<table id=\"board\">";
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

    windowResize();

}

function updateBoard(updateObject){
    for(cell of updateObject.cells){
        changeImage("#"+cell.x+cell.y,cell.image.path);
    }
}

function changeImage(id, url){
    $(id).attr("src",url);
}

function getImageString(cell){
    var out = "<img id=\"" + cell.x + cell.y + "\" src= \"" + cell.image.path + "\" style=\"height: auto, width:auto\" >"
    //var out = "hellp";
    return out;
}

$(document).ready( function(){
    fetchRoomKey();
    connectToRoom();

    $('#boardTable').click( function(event) {
        var target = $(event.target);
        $td = target.closest('td');

        var col   = $td.index();
        var row   = $td.closest('tr').index();

        flipFlopClient.send("/flipflop/click/"+roomKey, {}, JSON.stringify({'roomKey':roomKey, 'x': row,'y': col, 'version':localBoardVersion}));

    });

    $('#openGameDropDownMenu').click(function (event){
        showDropDown();
    })

    $('#closeGameDropDownMenu').click(function (event){
        closeDropDown();
    })






});

function showDropDown(){
    $('#openGameDropDownMenu').css("display","none");
    $('.gameControlObject').css("display","block");

}

function closeDropDown(){
    $('#openGameDropDownMenu').css("display","block");
    $('.gameControlObject').css("display","none");
}

function windowResize(){
    if ($(window).width() < $(window).height()*0.85) {
        $("#board").width("100%");
        $("#board").height($("#board").width());
    } else {
        /**
         * Scroll bar issue, since the window size change won't change the height of page(div) it will generate scroll
         * bar instead, thus change based on height(100%) is pretty dangerous.
         * */
        $("#board").width($(window).height()*0.85);
        $("#board").height($("#board").width());
    }
}

$(window).resize(function(){
    windowResize();
});

$(function (){
    $("#getNewGameButton").click(function (){
        startNewGame();
    });
});