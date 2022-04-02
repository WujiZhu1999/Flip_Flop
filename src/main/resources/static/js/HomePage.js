let nameKeyRegex = /[^A-Za-z0-9]+/

function joinNewGame(){
    let userName = $("#joinRoomUsername").val();
    if(nameKeyRegex.test(userName)) {
        alert("Name can only contain digits and letters.");
        return;
    }

    if(userName.length === 0) {
        alert("Name can not be empty.");
        return;
    }

    let roomKey = $("#joinRoomKey").val();
    if(nameKeyRegex.test(roomKey)) {
        alert("Name can only contain digits and letters.");
        return;
    }

    $("#joinGameForm").submit();

}

$(function () {
    $("#joinGameButton").click(function () {joinNewGame();});
});