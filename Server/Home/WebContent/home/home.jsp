<%--
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>


<html>
<head>
<script language = JavaScript>
   function go_room(num) {
      if(num == 1) {
         document.form1.link.value = "living_room";
         document.form1.submit();
      }
      else if(num == 2) {
         document.form1.link.value = "wash_room";
         document.form1.submit();
      }
      else if(num == 3) {
         document.form1.link.value = "main_room";
         document.form1.submit();
      }
      return;
   }
</script>
<title>Page Title</title>
<link rel="stylesheet" href="http://demos.jquerymobile.com/1.4.5/theme-classic/theme-classic.css" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
<div data-role="page" id="home">
 <div data-role="header" data-theme="e">
  <h1>SMART HOME</h1>
  <a href="#" data-role="button" data-mini="true" data-theme="c" class="ui-btn-right">LOGOUT</a>
 </div>
 <div data-role="content" data-theme="d">
<h3 style="color:#666;">현재 집의 전체 상황을 볼 수 있습니다.<br>방을 클릭하면 상세 정보와 상태를 변경하는 페이지로 이동합니다.</h3>

<form name="form1" method="post" action="/web_project/Controller" >
<input type="hidden" name="link" value="null"/>
<div>
<!-- 부엌 -->
<div style="width:310px; height:250px; border:solid black 1px; position:relative; left:0px; top:0px; text-align:center; cursor: pointer;" onclick="return go_room(1);">kitchen</div>
<!-- 현관 복도 -->
<div style="width:350px; height:50px; border:solid black 1px; position:relative; left:0px; top:0px; text-align:center; cursor: pointer;" onclick="return go_room(1);">entrance</div>
<!-- 거실 -->
<div style="width:390px; height:250px; border:solid black 1px; position:relative; left:312px; top:-304px; text-align:center; cursor: pointer;" onclick="return go_room(1);">living room</div>
<!-- 작은 방 -->
<div style="width:250px; height:195px; border:solid black 1px; position:relative; left:-352px; left:0px; top:-252px; text-align:center; cursor: pointer;" onclick="return go_room(3);">mini room</div>
</div>
<!-- 화장실 -->
<div style="width:98px; height:195px; border:solid black 1px; position:relative; text-align:center; left:252px; top:-449px; cursor: pointer;" onclick="return go_room(2);">wash room</div>
<!-- 큰 방 -->
<div style="width:351px; height:247.5px; border:solid black 1px; position:relative; left:350px; top:-699px; text-align:center; cursor: pointer;" onclick="return go_room(3);">main room</div>
</div>
</form>
<div></div>
 </div>
 <div data-role="footer" data-theme="e">
  <h4>1조 웹프로그래밍 프로젝트</h4>
 </div>
</div>
</body>
</html>
--%>

<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="web_project.controller.*"%>
<!DOCTYPE html>

<% SecureVO secureVO = (SecureVO) request.getAttribute("secureMode"); %>

<html>
<head>
<script language = JavaScript>
   function go_room(num) {
      if(num == 1) {
         document.form1.link.value = "kitchen";
         document.form1.submit();
      }
      else if(num == 2) {
         document.form1.link.value = "entrance";
         document.form1.submit();
      }
      else if(num == 3) {
         document.form1.link.value = "living_room";
         document.form1.submit();
      }
      else if(num == 4) {
    	  document.form1.link.value = "mini_room";
    	  document.form1.submit();
      }
      else if(num == 5) {
    	  document.form1.link.value = "wash_room";
    	  document.form1.submit();
      }
      else if(num == 6) {
    	  document.form1.link.value = "main_room";
    	  document.form1.submit();
      }
      return;
   }
</script>
<title>Page Title</title>
<link rel="stylesheet" href="http://demos.jquerymobile.com/1.4.5/theme-classic/theme-classic.css" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
<div data-role="page" id="home">
 <div data-role="header" data-theme="e">
  <h1>Intelligent House</h1>
 </div>
 <div data-role="content" data-theme="d">
<h3 style="color:#666;">현재 집의 전체 상황을 볼 수 있습니다.<br>방을 클릭하면 상세 정보와 상태를 변경하는 페이지로 이동합니다.</h3>

<form name="form1" method="post" action="/web_project/Controller" >
<input type="hidden" name="link" value="null"/>
<div>
<!-- 부엌 -->
<div style="width:310px; height:250px; border:solid black 1px; position:relative; left:0px; top:0px; text-align:center; cursor: pointer;" onclick="return go_room(1);">kitchen</div>
<!-- 현관 복도 -->
<!-- <div style="background:#fceda7; width:350px; height:50px; border:solid black 1px; position:relative; left:0px; top:0px; text-align:center; cursor: pointer;" onclick="return go_room(2);">entrance</div> -->
<div style="width:350px; height:50px; border:solid black 1px; position:relative; left:0px; top:0px; text-align:center; cursor: pointer;" onclick="return go_room(2);">entrance</div>
<!-- 거실 -->
<div style="width:390px; height:250px; border:solid black 1px; position:relative; left:312px; top:-304px; text-align:center; cursor: pointer;" onclick="return go_room(3);">living room</div>
<!-- 작은 방 -->
<div style="width:250px; height:195px; border:solid black 1px; position:relative; left:-352px; left:0px; top:-252px; text-align:center; cursor: pointer;" onclick="return go_room(4);">mini room</div>
<!-- 화장실 -->
<div style="width:98px; height:195px; border:solid black 1px; position:relative; text-align:center; left:252px; top:-449px; cursor: pointer;" onclick="return go_room(5);">wash room</div>
<!-- 큰 방 -->
<div style="width:351px; height:247.5px; border:solid black 1px; position:relative; left:350px; top:-699px; text-align:center; cursor: pointer;" onclick="return go_room(6);">main room</div>
</div>
<div style="width:350px; height:247.5px; position:relative; left:800px; top:-1209px; text-align:center; cursor: pointer;">
 <table>
 <caption><h2>보안상태</h2></caption>
	<tr>
	<th>
					<%
						if (secureVO.getMode() == 0) {
					%>
					<img src="./LedImage/trafficoff.png">
					<%
						} else {
					%>
					<img src="./LedImage/trafficon.png">
					<%
						}
					%>
	</th></tr>
 </table>
</div>
</form>
<div></div>
 </div>
 <div data-role="footer" data-theme="e">
  <h4>Intelligent House</h4>
 </div>

</div>
</body>
</html>