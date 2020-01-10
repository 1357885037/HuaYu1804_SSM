<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试</title>
    <script src="../js/jquery-3.3.1.js"></script>
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

                $.get("/deletepl.do?deletepls="+check_val+"",function(data){
                    window.location.href="/query.do";  //在当前界面打开界面  刷新

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

<form action="/query.do">
    书名：<input type="text" name="name"> 作者:<input type="text" name="zuoze">
    <input type="submit" value="搜索">
</form>
<a href="/addBookOne.do">添加</a>
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

    <#list list as stu >
        <tr>
            <td><input type="checkbox" name="duoxuan" value="${stu.number}" onclick="yiji(this)"></td>
            <td>${stu_index+1}</td>
            <td>${stu.name}</td>
            <td>${stu.zuoze}</td>
            <td>${stu.price}</td>
            <td>${stu.jianjie}</td>
            <td>${(stu.nid == 2)? string('历史类','政治类') }</td>
            <td>${stu.datebook}</td>
            <td><a href="/updateOne.do?number=${stu.number}">修改</a></td>
            <td>
                <a href="/delete.do?number=${stu.number}">删除</a>
            </td>
        </tr>
        </#list>
</table>
<a href="/query.do?dangqian=${pageInfo.pageNum-1}">上一页</a>
<#list  pageInfo.pages as page >
    <a href="#">${page_index}</a>
</#list>
<a href="/query.do?dangqian=${pageInfo.pageNum+1}">下一页</a>
</body>
</html>