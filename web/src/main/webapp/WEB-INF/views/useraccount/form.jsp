<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:choose>
  <c:when test="${readonly}">
<h4 class="header"><mytaglib:i18n key="userAccount.view" /></h4>
  </c:when>
  <c:otherwise>
<h4 class="header"><mytaglib:i18n key="userAccount.edit" /></h4>
  </c:otherwise>
</c:choose>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text"><mytaglib:i18n key="userAccount.persistenceException" /></h5>
</c:if>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/useraccount" />

<div class="row">
    <form:form class="col s12" method="POST" action="${baseUrl}" modelAttribute="formModel">
        <form:input path="id" type="hidden" />
        <div class="row">
            <div class="input-field col s12">
                <form:input path="email" type="email" data-error="wrong" data-success="right" disabled="${readonly}" />
                <form:errors path="email" cssClass="red-text" />
                <label for="email"><mytaglib:i18n key="email" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="password" type="password" class="validate" disabled="${readonly}" />
                <form:errors path="password" cssClass="red-text" />
                <label for="password"><mytaglib:i18n key="password" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                 <form:select path="role" disabled="${readonly}">
                    <form:options items="${roleChoices}" />
                </form:select>
                <form:errors path="role" cssClass="red-text" />
                <label for="role"><mytaglib:i18n key="role" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="firstName" type="text" disabled="${readonly}" />
                <form:errors path="firstName" cssClass="red-text" />
                <label for="firstName"><mytaglib:i18n key="firstName" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="lastName" type="text" disabled="${readonly}" />
                <form:errors path="lastName" cssClass="red-text" />
                <label for="lastName"><mytaglib:i18n key="lastName" /></label>
            </div>
        </div>      
        <div class="row">
            <div class="input-field col s12">
                <form:input path="birthday" type="text" disabled="${readonly}" cssClass="datepicker" />
                <form:errors path="birthday" cssClass="red-text" />
                <label for="birthday"><mytaglib:i18n key="birthday" /></label>
            </div>
        </div> 
        <div class="row">
            <div class="input-field col s12">
                <form:input path="address" type="text" disabled="${readonly}" />
                <form:errors path="address" cssClass="red-text" />
                <label for="address"><mytaglib:i18n key="address" /></label>
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
