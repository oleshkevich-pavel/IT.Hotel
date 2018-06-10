<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<h4 class="header"><mytaglib:i18n key="bookedMaintenance.new" /></h4>


<c:set var="baseUrl" value="${pageContext.request.contextPath}/bookedmaintenance" />

<div class="row">
    <form:form class="col s12" method="POST" action="${baseUrl}/newmaintenance/add" modelAttribute="formModel">
        <div class="row">
            <div class="input-field col s12">
                <form:select path="maintenanceId">
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
            <div class="col s6"></div>
            <div class="col s3">
                <c:if test="${!readonly}">
                    <button class="btn waves-effect waves-light right" type="submit"><mytaglib:i18n key="save" /><i class="material-icons right">save</i></button>
                </c:if>
            </div>
            <div class="col s3">
                <a class="btn waves-effect waves-light right" href="${pageContext.request.contextPath}/maintenancesearch"><i class="material-icons left">reply</i><mytaglib:i18n key="back" />
                </a>
            </div>
        </div>
    </form:form>
</div>
