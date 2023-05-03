<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: banghansol
  Date: 2023/04/12
  Time: 12:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>2023/04/12 12:10 PM</title>
    <style>
        textarea {resize: none;}
    </style>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
</head>
<body>
    <c:if test="${empty member}">
    <form method="post" action="login">
        <input name="id">
        <input name="pw" type="password">
        <button>로그인</button>
        <p>${msg}</p>
    </form>
    </c:if>
    <c:if test="${not empty member}">
        <p>로그인 됨</p>
        <p>${member.name} / ${member.id}</p>
        <a href="logout">로그아웃</a>
    <h1>chat client</h1>
    <form name="frm">
        <textarea rows="10" readonly name="area"></textarea><br>
        <input name="message" autofocus>
        <button>전송</button>
    </form>
    </c:if>
    <script>
        var ws = new WebSocket("ws://localhost:8080/chat");
        ws.onopen = function (ev) {
            console.log("연결완료", ev)
        }

        ws.onclose = function (ev) {
            console.log("연결종료", ev);
        }

        ws.onmessage = function (ev) {
            console.log(ev.data);

           frm.area.value = frm.area.value + ev.data + "\n";
        }

        $(function () {
            $(document.frm).submit(function () {
                event.preventDefault();
                var msg = this.message.value;
                this.message.value = "";
                var obj = {id:'id1', msg:msg}
                ws.send(JSON.stringify(obj));
                console.log(obj);
            })
        })
    </script>
</body>
</html>