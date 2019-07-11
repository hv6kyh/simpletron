<%@page import="java.util.concurrent.ExecutionException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

<%@ page import="simpletron.Simpletron" %> <!-- 심플트론 클래스 가져옴 -->
<%@ page import ="java.net.URLEncoder" %>

<%@ page import="java.io.PrintWriter" %> <!-- 자바 클래스 사용 -->

<% request.setCharacterEncoding("UTF-8"); %>



<!-- 한명의 회원정보를 담는 user클래스를 자바 빈즈로 사용 / scope:페이지 현재의 페이지에서만 사용-->

<jsp:useBean id="simpletron" class="simpletron.SimpletronIO" scope="page" />

<jsp:setProperty name="simpletron" property="instructionInput" />

<jsp:setProperty name="simpletron" property="valueInput" /> 



<!DOCTYPE html>

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 뷰포트 -->

<meta name="viewport" content="width=device-width" initial-scale="1">

<!-- 스타일시트 참조  -->

<link rel="stylesheet" href="css/bootstrap.min.css">

<title>jsp 게시판 웹사이트</title>

</head>

<body>

<!-- 네비게이션  -->

<%

		//로긴한사람이라면	 userID라는 변수에 해당 아이디가 담기고 그렇지 않으면 null값

		String userID = null;

		if (session.getAttribute("userID") != null) {

			userID = (String) session.getAttribute("userID");



		}

%> 

	<nav class="navbar navbar-default">

		<div class="navbar-header">

			<button type="button" class="navbar-toggle collapsed"

				data-toggle="collapse" data-target="bs-example-navbar-collapse-1"

				aria-expaned="false">

				<span class="icon-bar"></span> <span class="icon-bar"></span> <span

					class="icon-bar"></span>

			</button>

			<a class="navbar-brand" href="bbs.jsp">심플트론</a>

		</div>

		<div class="collapse navbar-collapse"

			id="#bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav">
				<%--
				<li><a href="main.jsp">메인</a></li>
				--%>
				<li><a href="bbs.jsp">예제</a></li>
				
				<li class="active"><a href="simpletron.jsp">실습</a></li>

			</ul>





			<%

				//라긴안된경우

				if (userID == null) {

			%>

			<ul class="nav navbar-nav navbar-right">

				<li class="dropdown"><a href="#" class="dropdown-toggle"

					data-toggle="dropdown" role="button" aria-haspopup="true"

					aria-expanded="false">접속하기<span class="caret"></span></a>

					<ul class="dropdown-menu">

						<li><a href="login.jsp">로그인</a></li>

						<li><a href="join.jsp">회원가입</a></li>

					</ul></li>

			</ul>

			<%

				} else {

			%>

			<ul class="nav navbar-nav navbar-right">

				<li class="dropdown"><a href="#" class="dropdown-toggle"

					data-toggle="dropdown" role="button" aria-haspopup="true"

					aria-expanded="false">회원관리<span class="caret"></span></a>

					<ul class="dropdown-menu">

						<li><a href="logoutAction.jsp">로그아웃</a></li>

					</ul></li>

			</ul>

			<%

				}

			%>

		</div>

	</nav>
 
 <!-- 네비게이션 끝 -->
 

	<%
	
		PrintWriter script = response.getWriter();
	
		// simpletron.jsp에 두 textarea의 값을 받아온다
		String inst = simpletron.getInstructionInput();
		String value = simpletron.getValueInput();
		
		// 값들이 유효한지 검사를 위한 변수
		int inputInstValid = 0;
		int inputValValid = 0;
		
		// 결과값 출력을 위한 변수
		String result = null;
		
		/*
		System.out.println("###############" + inst);
		System.out.println("###############" + value);
		*/

		// 줄바꿈을 기준으로 토큰을 나눔
		String[] splitInst = null;
		String[] splitVal = null;
		
		Simpletron st = new Simpletron();
		
		boolean isSuccess = false;
		
		script.println("<script>");
		
		//script.println("alert('알림ㅇㅇ')");
		
		try {
			splitInst = inst.split("\r\n");
		} catch (NullPointerException e) {
			script.println("alert('명령어를 입력하세요.')");
			script.println("history.back()");	
		}
		
		try {
			splitVal = value.split("\r\n");
		} catch (NullPointerException e) {
			script.println("alert('값을 입력하세요.')");
			script.println("history.back()");	
		}
		
		
		/*
		for (int i = 0; i < splitInst.length; i++)
			System.out.println(i + "번째 명령어 토큰 : " + splitInst[i] );
			
		for (int i = 0; i < splitVal.length; i++)
			System.out.println(i + "번째 값 토큰 : " + splitVal[i] );
		*/
		
		
		// 심플트론 클래스 생성
		
		//st.paramPrac(splitInst);
		//st.parsePrac(splitInst);
		
		// 토큰화한 명령어를 매개변수로 전달
		
		inputInstValid = st.inputInstruction(splitInst);

		
		// 명령어 입력 오류 발생 체크
		if ( inputInstValid == -1) {
			script.println("alert('숫자만 적어주세요.')");
			script.println("history.back()");
		}
		else if ( inputInstValid == 0 ) {
			script.println("alert('오버플로우가 발생했습니다.')");
			script.println("history.back()");
		}
		else if ( inputInstValid == 9) {
			script.println("alert('올바르지 않은 명령어입니다. 게시판을 참조하세요.')");
			script.println("history.back()");
		}
		else {		// 명령어 정상적으로 입력됨
		
		// 토큰화한 값들을 매개변수로 전달 -> 출력값 가져온다
			//isSuccess = true;
			result = st.execute(splitVal);
			//request.setAttribute("result", result);
		
		}
		
		//script.println("location.href = 'simpletronResult.jsp'");
		
		script.println("</script>");
	
		/*
		script.println("<script>");	
		script.println("alert(result)");
		//script.println("document.write(result)");				// 심플트론의 결과를 화면에 뿌린다
		//script.println("history.back()");	
		script.println("</script>");
		*/
		
		//out.println( result );
	

	%>
	
	<textarea readonly="readonly" cols="70" rows="30"><%out.println( result ); %></textarea>

	
	


 <!-- 애니매이션 담당 JQUERY -->

 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 

 <!-- 부트스트랩 JS  -->

 <script src="js/bootstrap.js"></script>
 
</body>

</html>