<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/css/materialize.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-beta/js/materialize.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/custom.css">
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<html>
<title>Error Page</title>
<body>
	<div class="row">
		<div class="col s12">
			<h5 class="center-align"><mytaglib:i18n key="error404" /></h5>
		</div>
		<div align="center" class="col s12">
			<img alt="404 page not found"
				src="https://5bucks.ru/wp-content/uploads/2017/06/404.png">
		</div>
		<div class="col s12">
			<h5 class="center-align">
			    <a class="btn waves-effect waves-light left" href="${pageContext.request.contextPath}"><mytaglib:i18n key="homepage" /></a>
			</h5>
		</div>
	</div>
</body>
</html>