<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- ����Ʈ -->

<meta name="viewport" content="width=device-width" initial-scale="1">

<!-- ��Ÿ�Ͻ�Ʈ ����  -->

<link rel="stylesheet" href="css/bootstrap.min.css">

<title>jsp �Խ��� ������Ʈ</title>


</head>
<body>

<!-- �׺���̼�  -->

<%

		//�α��ѻ���̶��	 userID��� ������ �ش� ���̵� ���� �׷��� ������ null��

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

			<a class="navbar-brand" href="bbs.jsp" >����Ʈ��</a>

		</div>

		<div class="collapse navbar-collapse"

			id="#bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav">
				<%--
				<li><a href="main.jsp">����</a></li>
				--%>
				<li><a href="bbs.jsp">����</a></li>
				
				<li><a href="simpletron.jsp">�ǽ�</a></li>

			</ul>





			<%

				//���ȵȰ��

				if (userID == null) {

			%>

			<ul class="nav navbar-nav navbar-right">

				<li class="dropdown"><a href="#" class="dropdown-toggle"

					data-toggle="dropdown" role="button" aria-haspopup="true"

					aria-expanded="false">�����ϱ�<span class="caret"></span></a>

					<ul class="dropdown-menu">

						<li><a href="login.jsp">�α���</a></li>

						<li><a href="join.jsp">ȸ������</a></li>

					</ul></li>

			</ul>

			<%

				} else {

			%>

			<ul class="nav navbar-nav navbar-right">

				<li class="dropdown"><a href="#" class="dropdown-toggle"

					data-toggle="dropdown" role="button" aria-haspopup="true"

					aria-expanded="false">ȸ������<span class="caret"></span></a>

					<ul class="dropdown-menu">

						<li><a href="logoutAction.jsp">�α׾ƿ�</a></li>

					</ul></li>

			</ul>

			<%

				}

			%>

		</div>

	</nav>
 
 <!-- �׺���̼� �� -->
 
 <div class="container">
 
 	<div class="row">
 	
 		<div class="col-md-8">
 		
 			<h1>����Ʈ���̶�?</h1>

			<p>
				����Ʈ���̶� 4�ڸ� �������� �̷���� �ڵ�� �� 2�ڸ��� opcode, �� 2�ڸ��� operand�� �ؼ��Ͽ� ����� �����ϴ� ���α׷��̴�.
				opcode�� ���� ǥ�� ������.
				
				
			</p>
		
			<p>
				<img alt="" src="image/simpletron.gif">
			</p>
	
 		
 		</div>
 		
 		<div class="col-md-4">
 			<div>
 				������ : �����������б�<br>1401059 �迵ȣ
 			</div>
 		</div>
 		
 	</div>
 	
 </div>
		


	
	
<!-- �ִϸ��̼� ��� JQUERY -->

 <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script> 

 <!-- ��Ʈ��Ʈ�� JS  -->

 <script src="js/bootstrap.js"></script>

</body>
</html>