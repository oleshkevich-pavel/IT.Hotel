<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<div class="row">
	<div class="input-field col s12 center">
		<h2>
			<mytaglib:i18n key="login.loginWithEmailAndPassword" />
		</h2>
	</div>
</div>
<div class="row">
	<div class="col s3"></div>
	<div class="col s6">
		<form name='loginForm' action="<c:url value='login' />" method='POST'>
			<div class="row">
				<div class="input-field col s12 center">
					<input type='text' name='email' value=''> <label
						for="email"><mytaglib:i18n key="email" />:</label>
				</div>
			</div>
			<div class="row">
				<div class="input-field col s12 center">
					<input type='password' name='password' /><label for="password"><mytaglib:i18n key="password" />:</label>
				</div>
			</div>
			<c:if test="${not empty error}">
				<div class="row">
					<div class="col s12 center">
						<div class="error">${error}</div>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="row">
					<div class="col s12 center">
						<div class="msg">${msg}</div>
					</div>
				</div>
			</c:if>
			<div class="row">
				<div class="col s12 center">
					<button class="btn waves-effect waves-light" type="submit"><mytaglib:i18n key="logIn" /></button>
				</div>
			</div>
			<div class="row">
				<div class="col s12 center">
					<a href="${pageContext.request.contextPath}/registration"><mytaglib:i18n key="registration" /></a>
				</div>
			</div>
		</form>
	</div>
	<div class="col s3"></div>
</div>