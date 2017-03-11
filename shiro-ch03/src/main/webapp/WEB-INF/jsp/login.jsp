
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <label class="error">${error}</label>
   <form action="/login" method="post">
       用户名：<input type="text" name="username" value="<shiro:principal/>"><p/>
       密码：<input type="password" name="password"><p/>
       <input type="checkbox" name="rememberMe">记住密码<p/>
       <input type="submit" value="登录">
   </form>
</body>
</html>
