document.write("<script language=javascript src='https://cdnjs.cloudflare.com/ajax/libs/layer/2.3/layer.js'></script>");
document.write("<script language=javascript src='https://raw.github.com/defunkt/jquery-pjax/master/jquery.pjax.js'></script>");

var page;
function innerLoad(id,url,data){
	$.ajaxSetup({cashe: false});
	$("#"+id).load(url+" #"+id,data,function(result){
		 $result = $(result); 
        /* $result.find("link").each(function(){
        	
         });*/
		 
		 alert($result.find("link"));
         
         $result.find("script").each(function(){
        	 alert($(this).attr("src"));
         });
         
		var regDetectJs = /<script(.|\n)*?>(.|\n|\r\n)*?<\/script>/ig;
		var jsContained = result.match(regDetectJs); //ajaxLoadedData为ajax获取到的数据
		// 第二步：如果包含js，则一段一段的取出js再加载执行
		if(jsContained) {
		  var regGetJS = /<script(.|\n)*?>((.|\n|\r\n)*)?<\/script>/im;
		  var jsNums = jsContained.length;
		  for (var i=0; i<jsNums; i++) {
		    var jsSection = jsContained[i].match(regGetJS);
		    if(jsSection[2]) {
		      if(window.execScript) {
		        // 给IE的特殊待遇
		        window.execScript(jsSection[2]);
		      } else {
		        // 给其他大部分浏览器用的
		        window.eval(jsSection[2]);
		      }
		    }
		  }
		}
	});
}

/**
 * @param pageId
 * page.header.id = "header";//网站头部id
 * page.header.url = "";//网站头部的url
 * page.sidebar.id  = "";//导航栏的id
 * page.sidebar.url = "";//导航栏地址
 * page.footer.id = "";//页脚
 * page.footer.url = "";//页脚地址
 * page.contentId = "";//内容的id
 */
function initPage(pageId){
	page = pageId;
	if(pageId.hasOwnProperty("header")){
		$("#"+page.header.id).load(page.header.url);
	}
	if(pageId.hasOwnProperty("sidebar")){
		innerLoad(page.sidebar.id,page.sidebar.url);
	}
	if(pageId.hasOwnProperty("footer")){
		$("#"+page.footer.id).load(page.footer.url);
	}
	if(!pageId.hasOwnProperty("contentId")){
		throw "the contentId of pageId must be exist";
	}
}
/**
 * 
 * @param url href所在标签
 * @param data href所带参数
 * @returns
 */
function load(url,data){
	innerLoad(page.contentId,$(url).attr("href"), data);
}


function checkNum(num){

	var reg=/^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 ，判断正整数用/^[1-9]+[0-9]*]*$/
	
	
	if(!reg.test(num)){
    	return false;
    }
	return true;
}
function checkPhone(phone){ 
    if(!(/^1(3|4|5|7|8)\d{9}$/.test(phone))){ 
        return false; 
    }
    return true;
}

function isEmpty(obj){
    if(typeof obj == "undefined" || obj == null || obj == ""){
        return true;
    }else{
        return false;
    }
}

function date(date) {
	var data1 = date.substr(6,10)+"/"+date.substr(0,5);
	return Date.parse(data1); //此时 da_1 = 1502121600000 毫秒数
}

function responseHandler(response) {
	if(response.errorCode !=0){
		 layer.msg('请求处理失败原因为：' + response.message);
	}else{
		layer.msg('业务处理成功');
	}
}
function keep2Decimals(number){
	if(number == null){
		return null;
	}
	return Math.round(number*100)/100;
}
var lock = false;
function postJson(url,jsonData){
	if(lock){
		layer.msg('正在处理中，请耐心等待服务器响应');
		return;
	}
	lock = true;
	$.ajax({
        type: "post",
        url: url,
        contentType: "application/json", //必须有
        dataType: "json", //表示返回值类型，不必须
        data: JSON.stringify(jsonData),
        success: function (jsonResult) {
        	responseHandler(jsonResult);
        	lock = false;
        },
        error:function(xhr,status,statusText){
        	layer.msg("请求出错 ："+xhr.status);
        }
    });
}
function deleteObject(url){
	 $.ajax({
		 type: 'delete',
		 url: url,
		 success: function(jsonResult){
			 responseHandler(jsonResult);
		 }
	 });
}



//返回 01-12 的月份值   
	
function getMonth(date){  
     var month = "";  
     month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
     if(month<10){  
         month = "0" + month;  
     }  
     return month;  
 }  
 //返回01-30的日期  
 function getDay(date){  
     var day = "";  
     day = date.getDate();  
     if(day<10){  
         day = "0" + day;  
     }  
     return day;  
 }
 function getHours(date){ 
 	 var hours = ""; 
 	 hours = date.getHours(); 
 	 if(hours<10){  
 	  hours = "0" + hours;  
 	 }  
 	 return hours;  
 	} 
 	//分 
 	function getMinutes(date){ 
 	 var minute = ""; 
 	 minute = date.getMinutes(); 
 	 if(minute<10){  
 	  minute = "0" + minute;  
 	 }  
 	 return minute;  
 	} 
 	//秒 
 	function getSeconds(date){ 
 	 var second = ""; 
 	 second = date.getSeconds(); 
 	 if(second<10){  
 	  second = "0" + second;  
 	 }  
 	 return second;  
 	}
 
 function dateFormat(longTypeDate){ 
 	  var dateType = ""; 
 	  var date = new Date(); 
 	  date.setTime(longTypeDate); 
 	  dateType += date.getFullYear();  //年 
 	  dateType += "/" + getMonth(date); //月  
 	  dateType += "/" + getDay(date);  //日 
 	  dateType += " " + getHours(date); //时  
 	  dateType += ":" + getMinutes(date);  //分 
 	  dateType += ":" + getSeconds(date);  //分 
 	  return dateType;
 }
 

