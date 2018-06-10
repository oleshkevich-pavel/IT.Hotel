<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:choose>
  <c:when test="${readonly}">
<h4 class="header"><mytaglib:i18n key="message.view" /></h4>
  </c:when>
  <c:otherwise>
<h4 class="header"><mytaglib:i18n key="message.edit" /></h4>
  </c:otherwise>
</c:choose>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/message" />

<div class="row">
    <form:form class="col s12" method="POST" action="${baseUrl}/add" modelAttribute="formModel">
        <form:input path="id" type="hidden" />
        <div class="row">
            <div class="input-field col s12">
                <form:input path="name" type="text" disabled="${readonly}" />
                <form:errors path="name" cssClass="red-text" />
                <label for="name"><mytaglib:i18n key="message.name" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="phone" type="text" disabled="${readonly}" />
                <form:errors path="phone" cssClass="red-text" />
                <label for="phone"><mytaglib:i18n key="phone" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="email" type="text" disabled="${readonly}" />
                <form:errors path="email" cssClass="red-text" />
                <label for="email"><mytaglib:i18n key="email" /></label>
            </div>
        </div>  
         <div class="row">
            <div class="input-field col s12">
                <form:textarea path="message" type="text" disabled="${readonly}" class="materialize-textarea"/>
                <form:errors path="message" cssClass="red-text" />
                <label for="message"><mytaglib:i18n key="message" /></label>
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
