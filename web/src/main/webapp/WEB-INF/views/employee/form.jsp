<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:choose>
  <c:when test="${readonly}">
<h4 class="header"><mytaglib:i18n key="employee.view" /></h4>
  </c:when>
  <c:otherwise>
<h4 class="header"><mytaglib:i18n key="employee.edit" /></h4>
  </c:otherwise>
</c:choose>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text"><mytaglib:i18n key="employee.persistenceException" /></h5>
</c:if>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/employee" />

<div class="row">
    <form:form class="col s12" method="POST" action="${baseUrl}" modelAttribute="formModel">
        <form:input path="id" type="hidden" />
        <div class="row">
            <div class="input-field col s12">
                <form:input path="userAccount.email"  type="email" data-error="wrong" disabled="${readonly}" />
                <form:errors path="userAccount.email" cssClass="red-text" />
                <label for="userAccount.email"><mytaglib:i18n key="email" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="userAccount.password" type="password" disabled="${readonly}" />
                <form:errors path="userAccount.password" cssClass="red-text" />
                <label for="userAccount.password"><mytaglib:i18n key="password" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:select path="postId" disabled="${readonly}">
                    <form:options items="${postChoices}" />
                </form:select>
                <form:errors path="postId" cssClass="red-text" />
                <label for="postId"><mytaglib:i18n key="post" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:select path="userAccount.role" disabled="${readonly}">
                    <form:options items="${employeeRoleChoices}" />
                </form:select>
                <form:errors path="userAccount.role" cssClass="red-text" />
                <label for="userAccount.role"><mytaglib:i18n key="role" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="userAccount.firstName" type="text" disabled="${readonly}" />
                <form:errors path="userAccount.firstName" cssClass="red-text" />
                <label for="userAccount.firstName"><mytaglib:i18n key="firstName" /></label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <form:input path="userAccount.lastName" type="text" disabled="${readonly}" />
                <form:errors path="userAccount.lastName" cssClass="red-text" />
                <label for="userAccount.lastName"><mytaglib:i18n key="lastName" /></label>
            </div>
        </div>      
        <div class="row">
            <div class="input-field col s12">
                <form:input path="userAccount.birthday" type="text" disabled="${readonly}" cssClass="datepicker" />
                <form:errors path="userAccount.birthday" cssClass="red-text" />
                <label for="userAccount.birthday"><mytaglib:i18n key="birthday" /></label>
            </div>
        </div> 
        <div class="row">
            <div class="input-field col s12">
                <form:input path="userAccount.address" type="text" disabled="${readonly}" />
                <form:errors path="userAccount.address" cssClass="red-text" />
                <label for="userAccount.address"><mytaglib:i18n key="address" /></label>
            </div>
        </div>  
        <div class="row">
            <div class="input-field col s12">
                <form:input path="userAccount.phone" type="text" disabled="${readonly}" />
                <form:errors path="userAccount.phone" cssClass="red-text" />
                <label for="userAccount.phone"><mytaglib:i18n key="phone" /></label>
            </div>
        </div>  
        <div class="row">
            <div class="input-field col s12">
                <form:input path="hiring" type="text" disabled="${readonly}" cssClass="datepicker" />
                <form:errors path="hiring" cssClass="red-text" />
                <label for="hiring"><mytaglib:i18n key="hiring" /></label>
            </div>
        </div> 
        <div class="row">
            <div class="input-field col s12">
                <form:input path="layoff" type="text" disabled="${readonly}" cssClass="datepicker" />
                <form:errors path="layoff" cssClass="red-text" />
                <label for="layoff"><mytaglib:i18n key="layoff" /></label>
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
