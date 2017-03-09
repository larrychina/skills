<%--
  Created by IntelliJ IDEA.
  User: Larry
  Date: 2017/3/9
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<shiro:user>
    [<shiro:principal/>]登录，<a href="${pageContext.request.contextPath}/logout">退出</a>
</shiro:user>
</body>
</html>
