<%--
  Created by IntelliJ IDEA.
  User: MI
  Date: 2019/11/29
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/updateTwo.do">
    <input type="hidden" name="number" value="${book.number}">

    名称：<input type="text" value="${book.name}"    name="name"><br>
    作者: <input type="text" value="${book.zuoze}"   name="zuoze"><br>
    价格: <input type="text" value="${book.price}"   name="price"><br>
    简介: <input type="text" value="${book.jianjie}" name="jianjie"><br>
    类别：<select name="nid"><c:forEach items="${list}" var="b" ><option value="${b.nid}"  ${b.nid eq book.nid ? 'selected' : ''}   >${b.nname}</option></c:forEach></select>
     <input type="submit" value=提交>

</form>



</body>
</html>
