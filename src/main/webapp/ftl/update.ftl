<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试</title>
</head>

<body>
<form action="/updateTwo.do">
    <input type="hidden" name="number" value="${book.number}">

    名称：<input type="text" value="${book.name}"    name="name"><br>
    作者: <input type="text" value="${book.zuoze}"   name="zuoze"><br>
    价格: <input type="text" value="${book.price}"   name="price"><br>
    简介: <input type="text" value="${book.jianjie}" name="jianjie"><br>
    类别：<select name="nid"><#list list as b ><option value="${b.nid}"  ${(b.nid == book.nid) ?string('selected', '')}   >${b.nname}</option></#list></select>
    <input type="submit" value=提交>

</form>
</body>
</html>