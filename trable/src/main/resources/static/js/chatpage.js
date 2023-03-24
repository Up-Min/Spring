/**
 *
 */

var socket;

$('#chating').scrollTop($('#chating').prop('scrollHeight'));

function wsOpen() {
  socket = new WebSocket("ws://localhost/chat");
  wsEvt();
}

function wsEvt() {
  socket.onopen = () => {
    console.log(socket);
  };

  socket.onmessage = function (data) {
    var msg = data.data;
    if (msg != null && msg.trim() != "") {
      $("#chating").append("<p>" + msg + "</p>");
    }

    document.addEventListener("keypress", function (e) {
      if (e.keyCode == 13) {
        send();
      }
    });
  };
}
function chatName() {
  var userName = $("#userName").val();
  if (userName == null || userName.trim == "") {
    alert("사용자 이름을 입력해주세요.");
    $("#userName").focus();
  } else {
    wsOpen();
    $("#yourName").hide();
    $("#yourMsg").show();
    socket.send(userName + " 님이 입장하셨습니다.");
  }
}
function send() {
  var uN = $("#userName").val();
  var msg = $("#chatting").val();
  if(msg == null || msg.trim == ""){
	  return false();
  }else{
  socket.send(uN + " " + msg);
  $("#chatting").val(" ");	  
  }
}
