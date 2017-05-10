/**
 * 
 */
package com.cyw.mammoth.core.page;

/**
 * @ClassName:Page.java
 *
 * @Description:
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-27下午6:30:11
 *
 */
public class Page {
	
	private int showCount = 10; // 每页显示记录数
	private int totalPage; // 总页数
	private int totalResult; // 总记录数
	private int currentPage; // 当前页
	private int currentResult; // 当前记录起始索引
	private boolean entityOrField; // true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private String pageStr; // 最终页面显示的底部翻页导航，详细见：getPageStr();
	private String pageUrl;
	
	private String pageHtml; // 显示分页样式

	public String getPageHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div style=\"float:right;margin-right:5px;\">");
		if (totalResult > 0) {
			if (currentPage == 1) {
				//sb.append("	<li class=\"pageinfo\">首页</li>\n");
				//sb.append("	<li class=\"pageinfo\">上页</li>\n");
			} else {
				sb.append("<button type=\"button\" class=\"btn btn-white\" onclick=\"gotoSrcForPage('"+ pageUrl +"', " + (currentPage - 1) + ")\"><i class=\"fa fa-chevron-left\"></i></button>");
			}
			int showTag = 3; // 分页标签显示数量
			int startTag = 1;
			if (currentPage >= showTag) {
					startTag = currentPage - 1;
			}
			int endTag = startTag + showTag - 1;
			for (int i = startTag; i <= totalPage && i <= endTag; i++) {
				if (currentPage == i) {
					sb.append("<button class=\"btn btn-white active\">"+ i +"</button>\n");
				} else {
					sb.append("<button class=\"btn btn-white\" onclick=\"javascript:gotoSrcForPage('"+ pageUrl +"', "+ i +");\">"+ i +"</button>\n");
					sb.append("<input type=\"hidden\" name=\"currentPage\" value=\""+ i +"\" />");
				}
			}
			if (currentPage == totalPage) {
				//sb.append("	<li class=\"pageinfo\">下页</li>\n");
				//sb.append("	<li class=\"pageinfo\">尾页</li>\n");
			} else {
				sb.append("<button type=\"button\" class=\"btn btn-white\" onclick=\"gotoSrcForPage('"+ pageUrl +"', " + (currentPage + 1) + ")\"><i class=\"fa fa-chevron-right\"></i></button>\n");
			}
			//sb.append("	<li class=\"pageinfo\">第" + currentPage + "页</li>\n");
			//sb.append("	<li class=\"pageinfo\">共" + totalPage + "页</li>\n");
			sb.append("<script type=\"text/javascript\">\n");
			sb.append("function nextPage(page){");
			sb.append("	if(false && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";}\n");
			sb.append("		else{url += \"?" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";}\n");
			sb.append("		document.forms[0].action = url+page;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";}\n");
			sb.append("		document.location = url + page;\n");
			sb.append("	}\n");
			sb.append("}\n");
			sb.append("function gotoFindPage(url){");
			sb.append("var re = /^[0-9]+?[0-9]*$/;");
			sb.append("var currentPage=$('#BasePageId').val();");
			sb.append("if(!re.test(currentPage)){");
			sb.append("toastr.error(\"请输入正确的页码\");");
			sb.append("return;");
			sb.append("}");
			sb.append("gotoSrcForPage(url,currentPage);");
			sb.append("}");
			sb.append("function getKeyUp(url){");
			sb.append("if(event.keyCode!=13){ ");
			sb.append("return;");
			sb.append("}");
			sb.append("var re = /^[0-9]+?[0-9]*$/;");
			sb.append("var currentPage=$('#BasePageId').val();");
			sb.append("if(!re.test(currentPage)){");
			sb.append("toastr.error(\"请输入正确的页码\");");
			sb.append("return;");
			sb.append("}");
			sb.append("gotoSrcForPage(url,currentPage);");
			sb.append("}");
			sb.append("</script>\n");
			sb.append("</div>");
			
//			sb.append("<select>");
//			for (int i = 0; i <= totalPage; i++) {
//				sb.append("<option onchange=\"gotoSrcForPage('"+ pageUrl +"', " + i +")>");
//				sb.append("第"+i+"页");
//				sb.append("</option>");
//			}
//			sb.append("</select>");
			
			
			sb.append("<div class=\"btn btn-white active\" style=\"margin-right:20px;cursor:auto;\">跳转：");
			sb.append("<input type='text'  style='width:70px;margin-right: 2px;' class='BasePageId' id='BasePageId' value='"+currentPage+"' maxlength='9' onkeypress=\"getKeyUp('"+pageUrl+"')\">");
			sb.append("<input type='button' value='确定' onclick=\"gotoFindPage('"+ pageUrl +"')\">");
			sb.append("</div>");

			//添加总页数和总记录数
			sb.append("<div class=\"btn btn-white active\" style=\"margin-right:20px;cursor:auto;\">总页数：" +
					    "<span style=\"color:#f8ac59;\">"+totalPage+"</span> 总记录数：<span style=\"color:#f8ac59;\">"+totalResult+"</span>" +
					  "</div>");
			
		} else {
			sb.append("无查询记录!\n");
		}
		pageHtml = sb.toString();		
		return pageHtml;
	}

	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public int getTotalPage() {
		if (totalResult % showCount == 0)
			totalPage = totalResult / showCount;
		else
			totalPage = totalResult / showCount + 1;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		if (totalResult > 0) {
			if (currentPage == 1) {
				//sb.append("	<li class=\"pageinfo\">首页</li>\n");
				//sb.append("	<li class=\"pageinfo\">上页</li>\n");
			} else {
				sb.append("<button type=\"button\" class=\"btn btn-white\"><i class=\"fa fa-chevron-left\" onclick=\"gotosrc('"+ pageUrl +"', " + (currentPage - 1) + ")\"></i></button>");
			}
			int showTag = 3; // 分页标签显示数量
			int startTag = 1;
			if (currentPage >= showTag) {
				startTag = currentPage - 1;
			}
			int endTag = startTag + showTag - 1;
			for (int i = startTag; i <= totalPage && i <= endTag; i++) {
				if (currentPage == i) {
					sb.append("<button class=\"btn btn-white active\">"+ i +"</button>\n");
				} else {
					sb.append("<button class=\"btn btn-white\" onclick=\"javascript:gotosrc('"+ pageUrl +"', "+ i +");\">"+ i +"</button>\n");
					sb.append("<input type=\"hidden\" name=\"currentPage\" value=\""+ i +"\" />");
				}
			}
			if (currentPage == totalPage) {
				//sb.append("	<li class=\"pageinfo\">下页</li>\n");
				//sb.append("	<li class=\"pageinfo\">尾页</li>\n");
			} else {
				sb.append("<button type=\"button\" class=\"btn btn-white\" onclick=\"gotosrc('"+ pageUrl +"', " + (currentPage + 1) + ")\"><i class=\"fa fa-chevron-right\"></i></button>\n");
			}
			//sb.append("	<li class=\"pageinfo\">第" + currentPage + "页</li>\n");
			//sb.append("	<li class=\"pageinfo\">共" + totalPage + "页</li>\n");
			
			sb.append("<script type=\"text/javascript\">\n");
			sb.append("function nextPage(page){");
			sb.append("	if(false && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";}\n");
			sb.append("		else{url += \"?" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";}\n");
			sb.append("		document.forms[0].action = url+page;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?" + (entityOrField ? "currentPage" : "page.currentPage") + "=\";}\n");
			sb.append("		document.location = url + page;\n");
			sb.append("	}\n");
			sb.append("}\n");
			sb.append("</script>\n");
		} else {
			sb.append("无查询记录!\n");
		}
		pageStr = sb.toString();
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getCurrentResult() {
		currentResult = (getCurrentPage() - 1) * getShowCount();
		if (currentResult < 0)
			currentResult = 0;
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

}
