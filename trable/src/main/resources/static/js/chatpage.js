var socket;

function wsOpen(userName) {
  socket = new WebSocket("ws://localhost/chat");
  wsEvt(userName);
}

function wsEvt(userName) {
  socket.onopen = () => {
    socket.send(userName + " 님이 입장하셨습니다.");
    console.log(socket);
  };

  socket.onmessage = function (data) {
    var msg = data.data;
    if (msg != null && msg.trim() != "") {
      $("#chating").append("<p>" + msg + "</p>");
    }
  };
  document.addEventListener("keypress", function (e) {
    if (e.keyCode == 13) {
      send();
    }
    let chat = document.querySelector("#chating");
    chat.scrollTop = chat.scrollHeight;
    console.log("chat", chat);
    console.log("scrollheight", chat.scrollHeight);
    console.log("chatscrollTop", chat.scrollTop);
  });
}

function send() {
  var uN = $("#userName").val();
  var msg = $("#chatting").val();
  if (msg == null || msg.trim == "") {
    return false();
  } else {
    socket.send(uN + " " + msg);
    msg.val = null;
  }
}

function chatName() {
  var userName = $("#userName").val();
  if (userName == null || userName.trim == "") {
    alert("사용자 이름을 입력해주세요.");
    $("#userName").focus();
  } else {
    wsOpen(userName);
    $("#yourName").hide();
    $("#yourMsg").show();
  }
}
//1
