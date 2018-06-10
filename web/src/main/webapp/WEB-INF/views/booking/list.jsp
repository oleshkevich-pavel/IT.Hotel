<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/nouislider.css">
<script
	src="${pageContext.request.contextPath}/resources/js/nouislider.js"></script>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/booking" />

<h4 class="header">
	<mytaglib:i18n key="bookings" />
</h4>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text">
		<mytaglib:i18n key="optimisticLockException" />
	</h5>
</c:if>

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="row">
			<div class="input-field col s12">
				<form:select path="roomNumberId">
					<option value="" selected><mytaglib:i18n key="any" /></option>
					<form:options items="${roomChoices}" />
				</form:select>
				<form:errors path="roomNumberId" cssClass="red-text" />
				<label for="roomNumberId"><mytaglib:i18n
						key="chooseRoomNumber" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="userAccountId">
					<option value="" selected><mytaglib:i18n key="any" /></option>
					<form:options items="${guestAccountChoices}" />
				</form:select>
				<form:errors path="userAccountId" cssClass="red-text" />
				<label for="userAccountId"><mytaglib:i18n key="chooseEmail" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="bookingStatusId">
					<option value="" selected><mytaglib:i18n key="any" /></option>
					<form:options items="${bookingStatusChoices}" />
				</form:select>
				<form:errors path="bookingStatusId" cssClass="red-text" />
				<label for="bookingStatusId"><mytaglib:i18n
						key="chooseBookingStatus" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="checkIn" type="text" cssClass="datepicker" />
				<label for="checkIn"><mytaglib:i18n key="checkIn" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="checkOut" type="text" cssClass="datepicker" />
				<label for="checkOut"><mytaglib:i18n key="checkOut" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="accomodation">
					<option value="" selected><mytaglib:i18n key="any" /></option>
					<form:options items="${accomodationChoices}" />
				</form:select>
				<form:errors path="accomodation" cssClass="red-text" />
				<label for="accomodation"><mytaglib:i18n
						key="chooseAccomodation" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:select path="view">
					<option value="" selected><mytaglib:i18n key="any" /></option>
					<form:options items="${viewChoices}" />
				</form:select>
				<form:errors path="view" cssClass="red-text" />
				<label for="view"><mytaglib:i18n key="chooseView" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<div id="booking-search-slider"
					class="noUi-target noUi-ltr noUi-horizontal"></div>
				<label for="booking-search"><mytaglib:i18n key="priceRange" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s4">
				<form:input path="priceMin" type="number" id="input-price-min" />
				<label for="priceMin"><mytaglib:i18n key="priceMin" /></label>
			</div>
			<div class="input-field col s4">
				<form:input path="priceMax" type="number" id="input-price-max" />
				<label for="priceMax"><mytaglib:i18n key="priceMax" /></label>
			</div>
		</div>
		<div class="col s12">
			<button class="btn waves-effect waves-light right" type="submit">
				<mytaglib:i18n key="search" />
				<i class="material-icons right">search</i>
			</button>
		</div>
	</form:form>
</div>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id">
					<mytaglib:i18n key="id" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="room">
					<mytaglib:i18n key="roomNumber" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}"
					column="userAccount">
					<mytaglib:i18n key="booking.userAccount" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="checkIn">
					<mytaglib:i18n key="checkIn" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="checkOut">
					<mytaglib:i18n key="checkOut" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="roomPrice">
					<mytaglib:i18n key="roomPrice" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}"
					column="bookingStatus">
					<mytaglib:i18n key="bookingStatus" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created">
					<mytaglib:i18n key="created" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated">
					<mytaglib:i18n key="updated" />
				</mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="booking" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${booking.id}" /></td>
				<td><c:out value="${booking.roomNumber}" /></td>
				<td><c:out value="${booking.userAccountEmail}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${booking.checkIn}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${booking.checkOut}" /></td>
				<td><c:out value="${booking.roomPrice}" /></td>
				<td><c:out value="${booking.bookingStatusName}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${booking.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${booking.updated}" /></td>
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${booking.id}"> <i class="material-icons">info</i>
				</a> <c:choose>
						<c:when test="${'оплачен' eq booking.bookingStatusName}">
							<a class="btn-floating disabled"
								href="${baseUrl}/${booking.id}/edit"> <i
								class="material-icons">edit</i></a>
							<a class="btn-floating red disabled"
								href="${baseUrl}/${booking.id}/delete"> <i
								class="material-icons">delete</i>
							</a>
						</c:when>
						<c:otherwise>
							<a class="btn-floating" href="${baseUrl}/${booking.id}/edit">
								<i class="material-icons">edit</i>
							</a>
							<a class="btn-floating red"
								href="${baseUrl}/${booking.id}/delete"> <i
								class="material-icons">delete</i>
							</a>
						</c:otherwise>
					</c:choose></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<mytags:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add">
	<i class="material-icons">add</i>
</a>
<script>
			var valueMin = document.getElementById('input-price-min');
			bookingSearchSlider.noUiSlider.on('update',
					function(values, handle) {
						var value = values[handle];
						if (handle) {
							valueMin.value = value;
						} else {
							valueMax.value = value;
						}
					});
		</script>
<script>
					var bookingSearchSlider = document.getElementById('booking-search-slider');
			noUiSlider.create(bookingSearchSlider, {
				start : [ ${slider.valueMin}, ${slider.valueMax} ],
				connect : true,
				step : 1,
				range : {
					'min' : 0,
					'max' : ${slider.max}
				}
			});
			</script>
<script>
			var valueMax = document.getElementById('input-price-max');
			bookingSearchSlider.noUiSlider.on('update',
					function(values, handle) {
						var value = values[handle];
						if (handle) {
							valueMax.value = value;
						} else {
							valueMin.value = value;
						}
					});
			priceMin.addEventListener('change', function() {
				bookingSearchSlider.noUiSlider.set([ this.value, null ]);
			});
			priceMax.addEventListener('change', function() {
				bookingSearchSlider.noUiSlider.set([ null, this.value ]);
			});
		</script>