<%--
  Created by IntelliJ IDEA.
  User: Larry
  Date: 2017/3/9
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        .error{color:red}
    </style>
</head>
<body>
<div class="error">${error}</div>
<form action="/login" method="post">
    用户名：<input type="text" name="userName"> <p/>
    密码：<input type="password" name="password"><p/>
    <input type="submit" value="登录">
</form>
</body>
</html>
