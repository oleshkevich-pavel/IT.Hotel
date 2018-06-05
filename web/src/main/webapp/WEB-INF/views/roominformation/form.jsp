<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/roominformation" />

<%-- <sec:authorize access="hasRole('ROLE_GUEST') or isAnonymous() ">
	<div class="row">
		<form:form class="col s8" method="POST"
			action="${baseUrl}/booking/mybooking" modelAttribute="bookingModel">
			<div class="input-field col s6">
				<form:input path="checkIn" type="text" cssClass="datepicker" />
				<form:errors path="checkIn" cssClass="red-text" />
				<label for="checkIn">дата заезда</label>
			</div>
			<div class="input-field col s6">
				<form:input path="checkOut" type="text" cssClass="datepicker" />
				<form:errors path="checkOut" cssClass="red-text" />
				<label for="checkOut">дата выезда</label>
			</div>
		</form:form>
		<div class="col s2">
			<sec:authorize access="hasRole('ROLE_GUEST')">
				<button class="btn waves-effect waves-light right" type="submit">
					забронировать <i class="material-icons right">add_shopping_cart</i>
				</button>
			</sec:authorize>
			<sec:authorize access="isAnonymous() ">
				<a class="waves-effect waves-light btn right"
					href="${pageContext.request.contextPath}/login">забронировать <i
					class="material-icons right">add_shopping_cart</i>
				</a>
			</sec:authorize>
		</div>
		<div class="col s2">
			<a class="btn waves-effect waves-light right"
				href="javascript:history.back();"><i class="material-icons left">reply</i><mytaglib:i18n key="back" /></a>
		</div>
	</div>
</sec:authorize> --%>
<br>
<div class="slider">
	<ul class="slides">
		<c:forEach var="photo" items="${photoLinks.list}"
			varStatus="loopCounter">
			<li><a class="carousel-item"><img class="materialboxed"
					src="${photo.link}"></a></li>
		</c:forEach>
		<c:if test="${photoLinks.totalCount eq 0}">
			<div class="caption center-align">
				<h3><mytaglib:i18n key="noPhotos" /></h3>
			</div>
		</c:if>
	</ul>
</div>

<div class="row">
	<div class="row">
		<div class="input-field col s12">
			<h4><mytaglib:i18n key="roomNumber" />:</h4>
			<c:out value="${formModel.number}" />
			<h4><mytaglib:i18n key="floor" />:</h4>
			<c:out value="${formModel.floor}" />
			<h4><mytaglib:i18n key="roomType" />:</h4>
			<c:out value="${formModel.type}" />
			<h4><mytaglib:i18n key="accomodation" />:</h4>
			<c:out value="${formModel.accomodation}" />
			<h4><mytaglib:i18n key="view" />:</h4>
			<c:out value="${formModel.view}" />
			<h4><mytaglib:i18n key="roomPrice" />:</h4>
			<c:out value="${formModel.actualPrice}" />
			<h4><mytaglib:i18n key="description" />:</h4>
			<c:out value="${formModel.description}" />
		</div>
	</div>

	<c:forEach var="comment" items="${comments.list}"
		varStatus="loopCounter">
		<div class="card-panel">
			<span class="blue-text text-darken-2"><c:out
					value="${comment.userAccountEmail}" /></span>
			<blockquote>
				<c:out value="${comment.comment}" />
			</blockquote>
		</div>
	</c:forEach>
	<sec:authorize access="!isAnonymous()">
		<div class="row">
		<sec:authentication var="userAccountId" property="id" />
			<form:form class="col s12" method="POST" action="${baseUrl}/${formModel.id}" modelAttribute="commentModel">
				<div class="input-field col s12">
				    <form:input path="id" type="hidden" />
					<form:input path='roomId' value='${formModel.id}' type="hidden"/>
				 	<form:input path='userAccountId' value='${userAccountId}' type="hidden"/>
					<form:textarea path="comment" type="text" class="materialize-textarea" />
					<form:errors path="comment" cssClass="red-text" />
					<label for="comment"><mytaglib:i18n key="comment" /></label>
				</div>
				<button class="btn waves-effect waves-light right" type="submit"><mytaglib:i18n key="postComment" /><i class="material-icons right">comment</i>
				</button>
			</form:form>
		</div>
	</sec:authorize>
	<div class="row">
		<div class="col s9"></div>
		<div class="col s3">
			<a class="btn waves-effect waves-light right"
				href="javascript:history.back();"><i class="material-icons left">reply</i><mytaglib:i18n key="back" /></a>
		</div>
	</div>
</div>
