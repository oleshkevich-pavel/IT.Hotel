<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<h4 class="header"><mytaglib:i18n key="myProfile" /></h4>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/guest/myprofile" />

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
 		<form:input path="userAccount.email" type="hidden" />
		<form:input path="userAccount.password" type="hidden" />
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
		<form:input path="verifyKey" type="hidden" />
		<form:input path="verified" type="hidden" />
		<form:input path="guestStatusId" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="credit" type="text" disabled="${readonly}" />
				<form:errors path="credit" cssClass="red-text" />
				<label for="credit"><mytaglib:i18n key="credit" /></label>
			</div>
		</div>
		<div class="row">
			<div class="col s6"></div>
			<div class="col s3">
				<c:if test="${!readonly}">
					<button class="btn waves-effect waves-light right" type="submit">
						<mytaglib:i18n key="save" /><i class="material-icons right">save</i>
					</button>
				</c:if>
			</div>
			<div class="col s3">
				<a class="btn waves-effect waves-light right"
					href="javascript:history.back();"><i
					class="material-icons left">reply</i><mytaglib:i18n key="back" /> </a>
			</div>
		</div>
	</form:form>
</div>
