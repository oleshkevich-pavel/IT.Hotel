<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<h4 class="header"><mytaglib:i18n key="registration.newGuest" /></h4>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text"><mytaglib:i18n key="registration.persistenceException" /></h5>
</c:if>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/registration" />

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="userAccount.email" type="email" data-error="wrong" />
				<form:errors path="userAccount.email" cssClass="red-text" />
				<label for="userAccount.email"><mytaglib:i18n key="email" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="userAccount.password" type="password" />
				<form:errors path="userAccount.password" cssClass="red-text" />
				<label for="userAccount.password"><mytaglib:i18n key="password" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="userAccount.firstName" type="text" />
				<form:errors path="userAccount.firstName" cssClass="red-text" />
				<label for="userAccount.firstName"><mytaglib:i18n key="firstName" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="userAccount.lastName" type="text" />
				<form:errors path="userAccount.lastName" cssClass="red-text" />
				<label for="userAccount.lastName"><mytaglib:i18n key="lastName" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="userAccount.birthday" type="text"
					cssClass="datepicker" />
				<form:errors path="userAccount.birthday" cssClass="red-text" />
				<label for="userAccount.birthday"><mytaglib:i18n key="birthday" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="userAccount.address" type="text" />
				<form:errors path="userAccount.address" cssClass="red-text" />
				<label for="userAccount.address"><mytaglib:i18n key="address" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="userAccount.phone" type="text" />
				<form:errors path="userAccount.phone" cssClass="red-text" />
				<label for="userAccount.phone"><mytaglib:i18n key="phone" /></label>
			</div>
		</div>
		<div class="row">
 			<div class="col s6">
				<div class="g-recaptcha"
					data-sitekey="6LcjfVoUAAAAANHUeelMXznTDWOMpjVUS6x3sWBg"></div>
			</div> 
			<div class="col s6">
				<button class="btn-large waves-effect waves-light center" type="submit"><mytaglib:i18n key="registration.signUp" /></button>
			</div>
		</div>
	</form:form>
</div>
