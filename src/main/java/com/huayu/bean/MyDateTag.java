package com.huayu.bean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class MyDateTag extends SimpleTagSupport{
	    String  url;
	    String  param;
	    Integer dangqian;
	    Integer zhongyeshu;
		String name;
	    String name_value;
		String zuoze;
		String zuoze_value2;


	
	

	public void doTag() throws JspException, IOException {
		
		Integer prePage=dangqian-1>0?dangqian-1:dangqian;
		Integer nextPage=dangqian+1>zhongyeshu?dangqian:dangqian+1;
	    String urlp=url+"?"+param+"="+prePage+"";
	    String urln=url+"?"+param+"="+nextPage+"";

		if(null != name && !"".equals(name) && null != name_value && !"".equals(name_value)){
			urlp+="&"+name+"="+name_value;
			urln+="&"+name+"="+name_value;
		}

		if(null != zuoze && !"".equals(zuoze) && null != zuoze_value2 && !"".equals(zuoze_value2)){
			urlp+="&"+zuoze+"="+zuoze_value2;
			urln+="&"+zuoze+"="+zuoze_value2;
		}



	    StringBuilder sb=new StringBuilder();
	    String str="";
	    sb.append("<a href='"+urlp+"'>上一页</a>");
	    for (int i = 1; i <=zhongyeshu; i++) {
			String urle=url+"?"+param+"="+i+"";
			str="<a href='"+urle+"'>"+i+"</a>";
			sb.append(str);
		}
	    sb.append("<a href='"+urln+"'>下一页 </a>");
		getJspContext().getOut().write(sb.toString());
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Integer getDangqian() {
		return dangqian;
	}

	public void setDangqian(Integer dangqian) {
		this.dangqian = dangqian;
	}

	public Integer getZhongyeshu() {
		return zhongyeshu;
	}

	public void setZhongyeshu(Integer zhongyeshu) {
		this.zhongyeshu = zhongyeshu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_value() {
		return name_value;
	}

	public void setName_value(String name_value) {
		this.name_value = name_value;
	}

	public String getZuoze() {
		return zuoze;
	}

	public void setZuoze(String zuoze) {
		this.zuoze = zuoze;
	}

	public String getZuoze_value2() {
		return zuoze_value2;
	}

	public void setZuoze_value2(String zuoze_value2) {
		this.zuoze_value2 = zuoze_value2;
	}
}
