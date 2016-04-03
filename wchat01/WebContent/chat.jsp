<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>chatRoom</title>
<script type="text/javascript"  src="jquery-1.4.4.min.js"></script>
<script type="text/javascript">
	var ws;
	var url = "ws://localhost:8080/wchat01/chatSocket?username=${sessionScope.username}";
	window.onload = content;
	function content(){
		if ('WebSocket' in window) {
            ws = new WebSocket(url);
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(url);
        } else {
            alert('WebSocket is not supported by this browser.');
            return;
        }
		ws.onmessage = function(event){
			eval("var result ="+event.data);
			
			if(result.alert != undefined){
				$("#content").append(result.alert+"<br/>");
			}
			
			if(result.names != undefined){
				$("#userList").html("");
				$(result.names).each(function(){
					$("#userList").append("<input type='checkbox' value='"+this+"' />"+this+"<br/>");
				});
			}
			
			if(result.from != undefined){
				$("#content").append(result.from+" "+result.date+" 说：<br/>"+result.sendMsg+"<br/>");
			}
		};
	}
	function send(){
		var value = $("#msg").val();
		ws.send(value);
	}
	
	function subSend(){
		var obj = null;
		var value = $("#msg").val();
		var to = $("#userList :checked");
		if(to.size()==0){
			obj ={
				chatType:1,
				to:to.val(),
				message:value
			};
		}else{
			obj ={
				chatType:2,
				to:to.val(),
				message:value
			};
		}
		
		ws.send(JSON.stringify(obj));
		$("#msg").val("")
	}
	
</script>
</head>
<body>
	<h3>欢迎 ${sessionScope.username } 使用本系统！</h3>
	<div id="content" 
		style="border:1px solid black;width:400px;height:300px;float:left;">
	</div>
	<div id="userList" 
		style="border:1px solid black;width:100px;height:300px;float:left;">
	</div>
	<div style="clear:both;">
		<input id="msg" /><button onclick="subSend();">Send</button>
	</div>
</body>
</html>