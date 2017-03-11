<%--
  Created by IntelliJ IDEA.
  User: Larry
  Date: 2017/3/9
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.layout.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("body").layout({ applyDemoStyles: true });
        });
    </script>
</head>
<body>
<div class="ui-layout-center">Center
</div>
<div class="ui-layout-north">权限集成框架</div>
<div class="ui-layout-west">菜单</div>

</body>
</html>
