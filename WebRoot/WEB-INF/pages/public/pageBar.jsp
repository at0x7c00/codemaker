<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
<!--
jQuery(function(){
	jQuery("#seek-first").button({
		icons : {
             primary: "ui-icon-seek-first"
        }
	});
	jQuery("#seek-prev").button({
		icons : {
             primary: "ui-icon-seek-prev"
        }
	});
	jQuery("#seek-next").button({
		icons : {
             primary: "ui-icon-seek-next"
        }
	});
	jQuery("#seek-end").button({
		icons : {
             primary: "ui-icon-seek-end"
        }
	});
	jQuery("button[vb='aa']").button();
	jQuery("#zhuan").button();
	jQuery("#goPage").bind("keydown",function(e){
		if((e.which <= 57 && e.which >= 46) || (e.which <= 40 && e.which >= 33) || e.which == 8 || e.which == 9)
			return true;
		return false; 
	});
});
//-->
</script>
		<div id="TableTail">
        <div id="TableTail_inside">
			<table width="${empty param.width?'100%':param.width }" cellpadding="0" cellspacing="0" border="0">
				<tr>
				   <td align="left">
						
				        <div id="PageSelectorMemo" style="display:inline">
						
						<span>共<strong>${pageBean.recordCount }</strong>条记录，当前第<strong>${pageBean.pageNum+1}</strong>页，共<strong>${pageBean.pageCount}</strong>页</span>
					</div>
				   </td>
				   <td align="right">
				       <div id="PageSelectorSelectorArea" style="display:inline">
							<button title="第一页" id="seek-first" onclick="gotoPageNum(1)"  ${pageBean.pageNum <= 0?'disabled':'' }>&nbsp;</button>
							<button title="上一页" id="seek-prev" onclick="gotoPageNum(${pageBean.pageNum})" ${pageBean.pageNum <= 0?'disabled':'' }>&nbsp;</button>
						<c:forEach begin="${pageBean.startIndex}"
							end="${pageBean.endIndex}" var="num">
								<button title="第${num}页" vb='aa' onclick="gotoPageNum(${num})" class="aa"
								   <c:if test="${(pageBean.pageNum+1)==num }">style="font-weight:blod;color:red;border:1px solid #ff0000" disabled</c:if>>${num}</button>
						</c:forEach>
							<button title="下一页" id="seek-next" onclick="gotoPageNum(${pageNum+2})" ${pageBean.pageNum >= pageBean.pageCount-1?'disabled':'' }>&nbsp;</button>
							<button title="最后页" id="seek-end" onclick="gotoPageNum(${pageBean.pageCount})" ${pageBean.pageNum >= pageBean.pageCount-1?'disabled':'' }>&nbsp;</button>
	                        每页<select style="width:50px" name="pageSize" onchange="go();">
	                        <option value="5" ${pageSize==5?'selected':''}>5</option>
	                        <option value="8" ${pageSize==8?'selected':''}>8</option>
	                        <option value="10" ${pageSize==10?'selected':''}>10</option>
	                        <option value="15" ${pageSize==15?'selected':''}>15</option>
	                        <option value="20" ${pageSize==20?'selected':''}>20</option>
	                        <option value="25" ${pageSize==25?'selected':''}>25</option>
	                        <option value="30" ${pageSize==30?'selected':''}>30</option>
	                        <option value="40" ${pageSize==40?'selected':''}>40</option>
	                        <option value="50" ${pageSize==50?'selected':''}>50</option>
	                        <option value="60" ${pageSize==60?'selected':''}>60</option>
	                        <option value="75" ${pageSize==75?'selected':''}>75</option>
	                        <option value="100"  ${pageSize==100?'selected':''}>100</option>
	                        <option value="200"  ${pageSize==200?'selected':''}>200</option>
	                        <option value="500"  ${pageSize==500?'selected':''}>500</option>
	                        <option value="1000"  ${pageSize==1000?'selected':''}>1000</option>
	                        <option value="2000"  ${pageSize==2000?'selected':''}>2000</option>
	                        <option value="5000"  ${pageSize==5000?'selected':''}>5000</option>
	                        <option value="10000"  ${pageSize==10000?'selected':''}>一万</option>
	                        <option value="100000"  ${pageSize==100000?'selected':''}>十万</option>
	                     </select>   
	                        条,转到<input onFocus="this.select();" size="1" type="text" value="${pageBean.pageNum+1}" id="goPage" tabindex="0" style="width: 30px; height: 18px; font-size: 12px; border: solid 1px #7aaebd;" />页
						 <button class="ButtonStyle2" title="跳转到指定页" onclick="go();" id="zhuan">转</button>
					</div>
				   </td>
				</tr>
			</table>
        </div>
    </div>
    
    
    
