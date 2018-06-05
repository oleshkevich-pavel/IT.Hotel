<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:choose>
	<c:when test="${readonly}">
		<h4 class="header"><mytaglib:i18n key="task.view" /></h4>
	</c:when>
	<c:otherwise>
		<h4 class="header"><mytaglib:i18n key="task.edit" /></h4>
	</c:otherwise>
</c:choose>

<c:if test="${'OptimisticLockException' eq error}">
	<h5 class="header red-text"><mytaglib:i18n key="optimisticLockException" /></h5>
</c:if>
<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text"><mytaglib:i18n key="optimisticLockException" /></h5>
</c:if>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/task" />

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<form:input path="version" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="toDo" type="text" disabled="${readonly}" />
				<form:errors path="toDo" cssClass="red-text" />
				<label for="toDo"><mytaglib:i18n key="task.toDo" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="description" type="text" disabled="${readonly}" />
				<form:errors path="description" cssClass="red-text" />
				<label for="description"><mytaglib:i18n key="description" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="executionDate" type="text" disabled="${readonly}"
					cssClass="datepicker" />
				<form:errors path="executionDate" cssClass="red-text" />
				<label for="executionDate"><mytaglib:i18n key="task.executionDate" /></label>
			</div>
		</div>
 		<div class="row">
			<div class="input-field col s12">
				<form:input path="executionTime" type="text" disabled="${readonly}"
					cssClass="timepicker" />
				<label for="executionTime"><mytaglib:i18n key="task.executionTime" /></label>	
			</div>
		</div> 
		<div class="row">
			<div class="input-field col s12">
				<form:select path="answerableId" disabled="${readonly}">
					<form:options items="${employeeChoices}" />
				</form:select>
				<form:errors path="answerableId" cssClass="red-text" />
				<label for="answerableId"><mytaglib:i18n key="task.answerable" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="priority" disabled="${readonly}">
					<form:options items="${priorityChoices}" />
				</form:select>
				<form:errors path="priority" cssClass="red-text" />
				<label for="priority"><mytaglib:i18n key="task.priority" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="creatorId" disabled="${readonly}">
					<form:options items="${employeeChoices}" />
				</form:select>
				<form:errors path="creatorId" cssClass="red-text" />
				<label for="creatorId"><mytaglib:i18n key="task.creator" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<div class="switch">
					<label><mytaglib:i18n key="task.notExecuted" /> <form:checkbox path="executed"
							disabled="${readonly}" /> <span class="lever"></span> <mytaglib:i18n key="task.executed" />
					</label>
				</div>
				<label class="switch-label"><mytaglib:i18n key="task.status" /></label> <br />
			</div>
		</div>
		<div class="row">
			<div class="col s6"></div>
			<div class="col s3">
				<c:if test="${!readonly}">
					<button class="btn waves-effect waves-light right" type="submit"><mytaglib:i18n key="save" /><i class="material-icons right">save</i></button>
				</c:if>
			</div>
			<div class="col s3">
				<a class="btn waves-effect waves-light right" href="javascript:history.back();"><i class="material-icons left">reply</i><mytaglib:i18n key="back" />
				</a>
			</div>
		</div>
	</form:form>
</div>
