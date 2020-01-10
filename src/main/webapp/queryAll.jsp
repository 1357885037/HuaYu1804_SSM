<%--
  Created by IntelliJ IDEA.
  User: MI
  Date: 2019/11/28
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://huayu.net/huayu04/mytld" prefix="mydate" %>
<html>
<head>
    <title>查询</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
</head>
<script>

    $(function () {

        $("#ceshi").click(function(){

            if(this.checked){
                $(":checkbox[name='duoxuan']").each(function(){
                    $(this).prop("checked",true);
                });
            }else{  //取消
                $(":checkbox[name='duoxuan']").each(function(){
                    $(this).prop("checked",false);
                });
            }

        })



        $("#delete").click(function(){


            obj=document.getElementsByName("duoxuan");

            check_val=[];
            for(k in obj){
                if(obj[k].checked){
                    check_val.push(obj[k].value)
                }
            }

            $.get("${pageContext.request.contextPath }/deletepl.do?deletepls="+check_val+"",function(data){
                window.location.href="${pageContext.request.contextPath }/query.do";  //在当前界面打开界面  刷新

            });

        })

    })

    function  yiji(e) {
        obj=document.getElementsByName("duoxuan");
        var i=0;
        for(k in obj){
            if(obj[k].checked){
                document.getElementById("ceshi").checked=true;
                i++;
            }
        }

        if(i==0){
            document.getElementById("ceshi").checked=false;
        }

    }
</script>
<body>
<form action="${pageContext.request.contextPath}/query.do">
书名：<input type="text" name="name"> 作者:<input type="text" name="zuoze">
<input type="submit" value="搜索">
</form>
<a href="addBookOne.do">添加</a>
<input type="button" id="delete" value="批量删除">
<table border="1">
    <tr>
        <td><input type="checkbox" id="ceshi" ></td>
        <td>编号</td>
        <td>名称</td>
        <td>作者</td>
        <td>价格</td>
        <td>简介</td>
        <td>类别</td>
        <td>入库时间</td>
        <td>操作</td>
    </tr>


    <c:forEach items="${list}" var="stu" varStatus="stat">
        <tr>
            <td><input type="checkbox" name="duoxuan" value="${stu.number}" onclick="yiji(this)"></td>
            <td>${stat.index+1}</td>
            <td>${stu.name}</td>
            <td>${stu.zuoze}</td>
            <td>${stu.price}</td>
            <td>${stu.jianjie}</td>
            <td>${stu.nid eq '2'? '历史类': '政治类' }</td>
            <td>${stu.datebook}</td>
            <td><a href="/updateOne.do?number=${stu.number}">修改</a></td>
            <td>
                   <a href="/delete.do?number=${stu.number}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<mydate:now url="${pageContext.request.contextPath}/query.do" param="dangqian" dangqian="${pageInfo.pageNum}" zhongyeshu="${pageInfo.pages}" name="name" name_value="${book.name}" zuoze="zuoze" zuoze_value2="${book.zuoze}"/>
当前第${pageInfo.pageNum}页!
</body>
</html>
