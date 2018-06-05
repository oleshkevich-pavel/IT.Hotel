<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:choose>
	<c:when test="${readonly}">
		<h4 class="header">
			<mytaglib:i18n key="photolink.view" />
		</h4>
	</c:when>
	<c:otherwise>
		<h4 class="header">
			<mytaglib:i18n key="photolink.edit" />
		</h4>
	</c:otherwise>
</c:choose>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text">
		<mytaglib:i18n key="photolink.persistenceException" />
	</h5>
</c:if>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/photolink" />

<div class="row">
	<form:form class="col s12" method="POST" enctype="multipart/form-data" action="${baseUrl}"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:select path="roomId" disabled="${readonly}">
					<form:options items="${roomChoices}" />
				</form:select>
				<form:errors path="roomId" cssClass="red-text" />
				<label for="roomId"><mytaglib:i18n key="roomNumber" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="userAccountId" disabled="${readonly}">
					<form:options items="${userAccountChoices}" />
				</form:select>
				<form:errors path="userAccountId" cssClass="red-text" />
				<label for="userAccountId"><mytaglib:i18n
						key="photolink.userAccount" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="link" type="text" disabled="${readonly}" />
				<form:errors path="link" cssClass="red-text" />
				<label for="link"><mytaglib:i18n key="link" /></label>
			</div>
		</div>
		<div class="row">
			<div class="col s6"></div>
			<div class="col s3">
				<c:if test="${!readonly}">
					<button class="btn waves-effect waves-light right" type="submit">
						<mytaglib:i18n key="save" />
						<i class="material-icons right">save</i>
					</button>
				</c:if>
			</div>
			<div class="col s3">
				<a class="btn waves-effect waves-light right"
					href="javascript:history.back();"><i
					class="material-icons left">reply</i>
				<mytaglib:i18n key="back" /> </a>
			</div>
		</div>
	</form:form>
</div>
