<%--
  Created by IntelliJ IDEA.
  User: banghansol
  Date: 2023/04/07
  Time: 10:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>2023/04/07 10:34 AM</title>
    <script src="${pageContext.request.contextPath }/resources/vendor/jquery/jquery.min.js"></script>
</head>
<body>
    <form method="post" enctype="multipart/form-data">
        <input type="file" name="files" multiple>
        <button>submit</button>
    </form>

</body>
</html>