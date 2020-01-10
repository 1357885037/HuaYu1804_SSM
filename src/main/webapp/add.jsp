<%--
  Created by IntelliJ IDEA.
  User: MI
  Date: 2019/11/29
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>添加:</h2>
<form action="${pageContext.request.contextPath}/addBookTwo.do" method="post">
<table id="sss">
    <tr><td>名称：<input type="text"    name="name"></td>
        <td>作者: <input type="text"    name="zuoze"></td>
        <td>价格: <input type="text"    name="price"></td>
        <td>简介: <input type="text"    name="jianjie"></td>>
        <td>类别：<select name="nid"><c:forEach items="${list}" var="b" ><option value="${b.nid}">${b.nname}</option></c:forEach></select></td>
    </tr>
</table>
    <input type="submit" value="提交">
</form>
</body>
</html>
