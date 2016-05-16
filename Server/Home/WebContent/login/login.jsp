<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 
 		서블릿 때문에 WEB-INF 안에있는 web.xml에 맵핑했음. 그리고 로그인페이지 디자인 css파일로 디자인 적용했음.
 		
		
 -->
<head>

<title>스마트홈 시스템 로그인 화면입니다.</title>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="/web_project/login/css/login.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<script type="application/x-javascript"> addEventListener("load", function() 
				{ setTimeout(hideURLbar, 0); }, false); 
		function hideURLbar(){ window.scrollTo(0,1); } </script>
		<!--webfonts-->
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:600italic,400,300,600,700' rel='stylesheet' type='text/css'>
		<!--//webfonts-->

</head>
<body>
<form method="post" action="/web_project/Controller" name="form1">
<input type=hidden name="login_check" value="false">
	 <!-----start-main---->
	 <div class="main">
		<div class="login-form">
			<h1>스마트홈 로그인</h1>
					<div class="head">
						<img src="/web_project/login/images/user.png" alt=""/>
					</div>
				<form>
						<input type="text" name="a_id" placeholder="아이디" >
						<input type="password" name="a_passwd"placeholder="비밀번호"> 
						
						
						<div class="submit">
							<input type="submit" onclick="myFunction()" value="LOGIN" >
							
					</div>	
	
				</form>
			</div>
			<!--//End-login-form-->
		
		</div>
			 <!-----//end-main---->

</body>
</html>