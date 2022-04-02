let nameKeyRegex = /[^A-Za-z0-9]+/

function joinNewGame(){
    var key = $("#joinRoomUsername").val();
    if(nameKeyRegex.test(key)) {
        alert("Name can only contain digits and letters.");
        return;
    }

    if(key.length === 0) {
        alert("Name can not be empty.");
        return;
    }


    alert("submitted");
}

$(function () {
    $("#joinForm").submit(function () {joinNewGame();});
});