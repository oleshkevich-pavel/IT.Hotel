<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:choose>
  <c:when test="${readonly}">
<h4 class="header"><mytaglib:i18n key="bookedMaintenance.view" /></h4>
  </c:when>
  <c:otherwise>
<h4 class="header"><mytaglib:i18n key="bookedMaintenance.edit" /></h4>
  </c:otherwise>
</c:choose>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/bookedmaintenance" />

<div class="row">
    <form:form class="col s12" method="POST" action="${baseUrl}" modelAttribute="formModel">
        <form:input path="id" type="hidden" />
        <div class="row">
            <div class="input-field col s12">
                <form:select path="userAccountId" disabled="${readonly}">
                    <form:options items="${guestAccountChoices}" />
                </form:select>
                <form:errors path="userAccountId" cssClass="red-text" />
                <label for="userAccountId"><mytaglib:i18n key="bookedMaintenance.userAccount" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:select path="maintenanceId" disabled="${readonly}">
                    <form:options items="${maintenanceChoices}" />
                </form:select>
                <form:errors path="maintenanceId" cssClass="red-text" />
                <label for="maintenanceId"><mytaglib:i18n key="maintenance" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="date" type="text" disabled="${readonly}" cssClass="datepicker" />
                <form:errors path="date" cssClass="red-text" />
                <label for="date"><mytaglib:i18n key="bookedMaintenance.date" /></label>
            </div>
        </div>  
        <div class="row">
			<div class="input-field col s12">
				<form:input path="time" type="text" disabled="${readonly}" cssClass="timepicker" />
				<form:errors path="time" cssClass="red-text" />
				<label for="time"><mytaglib:i18n key="bookedMaintenance.time" /></label>	
			</div>
		</div>        
        <div class="row">
            <div class="input-field col s12">
                <form:input path="price" type="text" disabled="${readonly}" />
                <form:errors path="price" cssClass="red-text" />
                <label for="price"><mytaglib:i18n key="price" /></label>
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
                <a class="btn waves-effect waves-light right" href="${baseUrl}"><i class="material-icons left">reply</i><mytaglib:i18n key="back" />
                </a>
            </div>
        </div>
    </form:form>
</div>
