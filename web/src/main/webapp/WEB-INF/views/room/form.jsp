<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/room" />

<c:choose>
	<c:when test="${readonly}">
		<h4 class="header"><mytaglib:i18n key="room.view" /></h4>
<%-- 		<sec:authorize access="hasRole('ROLE_GUEST') or isAnonymous() ">
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
							href="${pageContext.request.contextPath}/login">забронировать
							<i class="material-icons right">add_shopping_cart</i>
						</a>
					</sec:authorize>
				</div>
				<div class="col s2">
					<a class="btn waves-effect waves-light right"
						href="javascript:history.back();"><i
						class="material-icons left">reply</i><mytaglib:i18n key="back" /></a>
				</div>
			</div>
		</sec:authorize>

		<div class="slider">
			<ul class="slides">
				<c:forEach var="photo" items="${photoLinks.list}"
					varStatus="loopCounter">
					<li><a class="carousel-item"><img class="materialboxed"
							src="${photo.link}"></a></li>
				</c:forEach>
				<c:if test="${photoLinks.totalCount eq 0}">
					<div class="caption center-align">
						<h3>No photo available for this number</h3>
					</div>
				</c:if>
			</ul>
		</div> --%>

	</c:when>
	<c:otherwise>
		<h4 class="header"><mytaglib:i18n key="room.edit" /></h4>
	</c:otherwise>
</c:choose>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text"><mytaglib:i18n key="room.persistenceException" /></h5>
</c:if>

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="number" type="number" disabled="${readonly}" />
				<form:errors path="number" cssClass="red-text" />
				<label for="number"><mytaglib:i18n key="roomNumber" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="floor" type="number" disabled="${readonly}" />
				<form:errors path="floor" cssClass="red-text" />
				<label for="floor"><mytaglib:i18n key="floor" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="type" disabled="${readonly}">
					<form:options items="${roomTypeChoices}" />
				</form:select>
				<form:errors path="type" cssClass="red-text" />
				<label for="type"><mytaglib:i18n key="roomType" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="accomodation" disabled="${readonly}">
					<form:options items="${accomodationChoices}" />
				</form:select>
				<form:errors path="accomodation" cssClass="red-text" />
				<label for="accomodation"><mytaglib:i18n key="accomodation" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="view" disabled="${readonly}">
					<form:options items="${viewChoices}" />
				</form:select>
				<form:errors path="view" cssClass="red-text" />
				<label for="view"><mytaglib:i18n key="view" /></label>
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
				<form:textarea path="description" type="text" disabled="${readonly}"
					class="materialize-textarea" />
				<form:errors path="description" cssClass="red-text" />
				<label for="description"><mytaglib:i18n key="description" /></label>
			</div>
		</div>
		<div class="row">
            <div class="input-field col s12">
                 <div class="switch">
                      <label> <mytaglib:i18n key="room.clean" /> <form:checkbox path="dirty" disabled="${readonly}" /> <span class="lever"></span> <mytaglib:i18n key="room.dirty" />
                      </label>
                 </div>
                 <label class="switch-label"><mytaglib:i18n key="room.getDirty" /></label> <br/>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                 <div class="switch">
                      <label> <mytaglib:i18n key="room.dontNeedRepair" /> <form:checkbox path="broken" disabled="${readonly}" /> <span class="lever"></span> <mytaglib:i18n key="room.needRepair" />
                      </label>
                 </div>
                 <label class="switch-label"><mytaglib:i18n key="room.repair" /></label> <br/>
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
					class="material-icons left">reply</i><mytaglib:i18n key="back" /></a>
			</div>
		</div>
	</form:form>
</div>
