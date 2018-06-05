<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/custom.css">

<html>
<title>Error Page</title>
<body>
	<div class="row">
		<div class="col s12">
			<h5 class="center-align">Ошибка 500. Internal Server Error!</h5>
			<p>
			<h5 class="center-align">Что-то пошло не так.</h5>
		</div>
	</div>
	<div class="row">
		<div align="center" class="col s12">
			<img alt="500 internal server error"
				src="http://watafak.ru/uploads/posts/2014-05/1399599220_simpsons-1.gif">
		</div>
	</div>
</body>
</html>