package com.huayu.dao;

import com.huayu.bean.Book;
import com.mysql.jdbc.StringUtils;

public class SqlProvider {

    public  String select(Book book){
        StringBuffer str=new StringBuffer("select * from book where 1=1");
        if (!StringUtils.isNullOrEmpty(book.getName())){
            str.append(" and name like '%"+book.getName()+"%' ");
        }

        if(!StringUtils.isNullOrEmpty(book.getZuoze())){
            str.append(" and zuoze like '%"+book.getZuoze()+"%' ");
        }

        return str.toString();
    }

    public String deletepl(String nn){
        StringBuffer str=null;
        String str2=null;
        if(!StringUtils.isNullOrEmpty(nn)){
            System.out.println(nn);
            String [] ss=nn.split(",");
            str=new StringBuffer("delete from book where number in ");
            str.append(" (");

            for (String s : ss) {
                str.append(" '"+s+"', ");
            }
            str2=str.substring(0,str.lastIndexOf(","))+")";
        }
        return str2;
    }

}
