<#import "spring.ftl" as s/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试</title>
</head>

<body>
<form action="/addBookTwo.do" method="post">
    <table id="sss">
        <tr><td>名称：<input type="text"    name="name"></td>
            <td>作者: <input type="text"    name="zuoze"></td>
            <td>价格: <input type="text"    name="price"></td>
            <td>简介: <input type="text"    name="jianjie"></td>>
            <td>类别：<select name="nid"><#list list as b ><option value="${b.nid}">${b.nname}</option></#list></select></td>
        </tr>
    </table>
    <input type="submit" value="提交">
</form>
</body>
</html>