<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="web_project.home.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>작은 방</title>
<link rel="stylesheet" href="http://demos.jquerymobile.com/1.4.5/theme-classic/theme-classic.css" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<%
 	SensorBean bean = (SensorBean)request.getAttribute("sensorBean");
	LedBean bean2 = (LedBean)request.getAttribute("ledBean");
// 	System.out.println(bean2.getR_name());
%>
<script>
	$(document).ready(function() {
		$('#slider1').on('change', function(){
			$.ajax({url: "/web_project/Controller?link=mini_room&action=update&s_id=" + <%=bean.getS_aid()%> + "&sensor=led&state=" + $('#slider1').val() , success: function(result){
		   //     alert(result);
		    }});
			
		});
	});
</script>
</head>
<body>
<div data-role="page" id="home">
 <div data-role="header" data-theme="e">
 	<a href="javascript:history.go(-1);" data-icon="arrow-l" data-role="button" data-mini="true" data-theme="c" class="ui-btn-left">HOME</a>
 	<h1>Intelligent House</h1>
 </div>
 
 <div data-role="content" data-theme="d" style="width:100%; height:120vh;">


	<div style="position:absolute;">
<!-- 부엌 -->
<div style="width:310px; height:250px; border:solid black 1px; position:relative; left:0px; top:0px; text-align:center;">kitchen</div>
<!-- 현관 복도 -->
<div style="width:350px; height:50px; border:solid black 1px; position:relative; left:0px; top:0px; text-align:center;">entrance</div>
<!-- 거실 -->
<div style="width:390px; height:250px; border:solid black 1px; position:relative; left:312px; top:-304px; text-align:center;">living room</div>
<!-- 작은 방 -->
<div style="background:#fceda7; width:250px; height:195px; border:solid black 1px; position:relative; left:-352px; left:0px; top:-252px; text-align:center;">mini room</div>
<!-- 화장실 -->
<div style="width:98px; height:195px; border:solid black 1px; position:relative; text-align:center; left:252px; top:-449px;">wash room</div>
<!-- 큰 방 -->
<div style="width:351px; height:247.5px; border:solid black 1px; position:relative; left:350px; top:-699px; text-align:center;">main room</div>
	</div>

	<div style="position:absolute; top:570px; width:100%;">
		<h2>작은 방</h2>
		상태 
		<div>
			<hr>
			
			<div data-role="fieldcontain">
				<label for="flip-1" style="width:40px;">전등</label> 
				<select id="slider1" name="slider1" data-role="slider" s_id="<%=bean.getS_aid()%>">
					<option value="0">off</option>
					<option value="1" <% if(bean2.getR_state() == 1) { %> selected="selected" <% } %>>on</option>
				</select>
			</div>
			
			<form name=form1 method=post action=/web_project/Controller> <!-- 중앙으로? 룸1컨트롤러로? -->
				<input type="hidden" name="link" value="homeController"/>
			</form>
		</div>
	</div>
 </div>
 <div data-role="footer" data-theme="e">
  <h4>Intelligent House</h4>
 </div>
</div>
</body>
</html>