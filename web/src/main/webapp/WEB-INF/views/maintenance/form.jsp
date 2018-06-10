<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:choose>
  <c:when test="${readonly}">
<h4 class="header"><mytaglib:i18n key="maintenance.view" /></h4>
  </c:when>
  <c:otherwise>
<h4 class="header"><mytaglib:i18n key="maintenance.edit" /></h4>
  </c:otherwise>
</c:choose>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/maintenance" />

<div class="row">
    <form:form class="col s12" method="POST" action="${baseUrl}/add" modelAttribute="formModel">
        <form:input path="id" type="hidden" />
        <div class="row">
            <div class="input-field col s12">
                <form:input path="name" type="text" disabled="${readonly}" />
                <form:errors path="name" cssClass="red-text" />
                <label for="name"><mytaglib:i18n key="maintenance.name" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="actualPrice" type="number" disabled="${readonly}" />
                <form:errors path="actualPrice" cssClass="red-text" />
                <label for="actualPrice"><mytaglib:i18n key="actualPrice" /></label>
            </div>
        </div>
        <div class="row">
			<div class="input-field col s12">
				<form:input path="photoLink" type="text" disabled="${readonly}" />
				<form:errors path="photoLink" cssClass="red-text" />
				<label for="photoLink"><mytaglib:i18n key="link" /></label>
			</div>
		</div>
        <div class="row">
            <div class="input-field col s12">
                 <div class="switch">
                      <label> <mytaglib:i18n key="maintenance.unavailable" /> <form:checkbox path="available" disabled="${readonly}" /> <span class="lever"></span> <mytaglib:i18n key="maintenance.available" />
                      </label>
                 </div>
                 <label class="switch-label"><mytaglib:i18n key="maintenance.availability" /></label> <br/>
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
