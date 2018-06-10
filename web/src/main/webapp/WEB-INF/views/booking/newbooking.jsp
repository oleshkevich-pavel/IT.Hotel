<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<h4 class="header"><mytaglib:i18n key="booking.newBooking" /></h4>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text"><mytaglib:i18n key="booking.persistenceException" /></h5>
</c:if>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/booking" />

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}/newbooking/add"
		modelAttribute="formModel">
		<div class="row">
			<div class="input-field col s12">
				<form:select path="roomId" id="roomId" >
					<form:options items="${roomChoices}" />
				</form:select>
				<form:errors path="roomId" cssClass="red-text" />
				<label for="roomId"><mytaglib:i18n key="roomNumber" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s6">
				<form:input path="checkIn" type="text" cssClass="datepicker" />
				<form:errors path="checkIn" cssClass="red-text" />
				<label for="checkIn"><mytaglib:i18n key="checkIn" /></label>
			</div>
			<div class="input-field col s6">
				<form:input path="checkOut" type="text" cssClass="datepicker" />
				<form:errors path="checkOut" cssClass="red-text" />
				<label for="checkOut"><mytaglib:i18n key="checkOut" /></label>
			</div>
		</div>
		<div class="row">
			<div class="col s6"></div>
			<div class="col s3">
				<button class="btn waves-effect waves-light right" type="submit">
					<mytaglib:i18n key="roomSearch.book" /><i class="material-icons right">add_shopping_cart</i>
				</button>
			</div>
			<div class="col s3">
				<a class="btn waves-effect waves-light right" href="${pageContext.request.contextPath}/roomsearch"><i
					class="material-icons left">reply</i>
				<mytaglib:i18n key="back" /> </a>
			</div>
		</div>
	</form:form>
</div>