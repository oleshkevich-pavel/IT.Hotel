<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/nouislider.css">
<script
	src="${pageContext.request.contextPath}/resources/js/nouislider.js"></script>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/roomsearch" />

<div class="col s3">
	<!-- 	<div class="fix_block"> -->
	<ul class="collapsible popout">
		<c:choose>
			<c:when
				test="${(not empty searchFormModel.description)or(not empty searchFormModel.comment)}">
				<li>
			</c:when>
			<c:otherwise>
				<li class="active">
			</c:otherwise>
		</c:choose>
		<div class="collapsible-header">
			<i class="material-icons">search</i>
			<mytaglib:i18n key="roomSearch.searchByParameters" />
		</div>
		<div class="collapsible-body">
			<form:form method="POST" action="${baseUrl}"
				modelAttribute="searchFormModel">
				<div class="row">
					<div class="input-field col s12">
						<form:input path="checkIn" type="text" cssClass="datepicker" />
						<form:errors path="checkIn" cssClass="red-text" />
						<label for="checkIn"><mytaglib:i18n key="checkIn" /></label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s12">
						<form:input path="checkOut" type="text" cssClass="datepicker" />
						<form:errors path="checkOut" cssClass="red-text" />
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
						<label for="booking-search"><mytaglib:i18n
								key="priceRange" /></label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s6">
						<form:input path="priceMin" type="number" id="input-price-min" />
						<label for="priceMin"><mytaglib:i18n key="priceMin" /></label>
					</div>
					<div class="input-field col s6">
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
				<br>

			</form:form>
		</div>
		</li>
		<c:choose>
			<c:when test="${not empty searchFormModel.description}">
				<li class="active">
			</c:when>
			<c:otherwise>
				<li>
			</c:otherwise>
		</c:choose>
		<div class="collapsible-header">
			<i class="material-icons">format_quote</i>
			<mytaglib:i18n key="roomSearch.searchByDescription" />
		</div>
		<div class="collapsible-body">
			<form:form method="POST" action="${baseUrl}"
				modelAttribute="searchFormModel">
				<div class="col s12">
					<form:input path="description" type="text" id="description" />
					<label for="description"><mytaglib:i18n
							key="roomSearch.searchByDescription" /></label>
				</div>
				<div class="col s12">
					<button class="btn waves-effect waves-light right" type="submit">
						<mytaglib:i18n key="search" />
						<i class="material-icons right">search</i>
					</button>
				</div>
				<br>
				<br>
				<br>
				<br>

			</form:form>
		</div>
		</li>
		<c:choose>
			<c:when test="${not empty searchFormModel.comment}">
				<li class="active">
			</c:when>
			<c:otherwise>
				<li>
			</c:otherwise>
		</c:choose>
		<div class="collapsible-header">
			<i class="material-icons">comment</i>
			<mytaglib:i18n key="roomSearch.searchByComment" />
		</div>
		<div class="collapsible-body">
			<form:form method="POST" action="${baseUrl}"
				modelAttribute="searchFormModel">
				<div class="col s12">
					<form:input path="comment" type="text" id="comment" />
					<label for="comment"><mytaglib:i18n
							key="roomSearch.searchByComment" /></label>
				</div>
				<div class="col s12">
					<button class="btn waves-effect waves-light right" type="submit">
						<mytaglib:i18n key="search" />
						<i class="material-icons right">search</i>
					</button>
				</div>
				<br>
				<br>
				<br>
				<br>

			</form:form>
		</div>
		</li>
	</ul>

</div>
<!-- </div>  -->

<div class="col s9">
	<c:if test="${empty roomsWithPhotoLinks}">
		<h4>
			<mytaglib:i18n key="roomSearch.noRoomsFound" />
		</h4>
	</c:if>
	<c:if test="${'PersistenceException' eq error}">
		<h5 class="header red-text">
			<mytaglib:i18n key="roomSearch.persistenceException" />
		</h5>
	</c:if>
	<mytags:paging />
	<c:forEach var="entry" items="${roomsWithPhotoLinks}">
		<c:set var="room" value="${entry.key}" />
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="card-image">
						<div class="slider">
							<ul class="slides">
								<c:forEach var="photo" items="${entry.value.list}"
									varStatus="loopCounter">
									<li><img class="materialboxed" src="${photo.link}">
										<span class="card-title"><c:out value="${room.number}" /></span></li>
								</c:forEach>
								<c:if test="${entry.value.totalCount==0}">
									<div class="caption center-align">
										<h3>
											<mytaglib:i18n key="noPhotos" />
											<c:out value="${room.number}" />
										</h3>
									</div>
								</c:if>
							</ul>
						</div>
					</div>

					<div class="card-content">
						<p>
							<c:out value="${room.description}" />
						</p>
					</div>
					<div class="card-action">
						<a href="${pageContext.request.contextPath}/roominformation/${room.id}">
					    	<mytaglib:i18n key="roomSearch.moreRoomDetails" /></a>
						<sec:authorize access="hasRole('ROLE_GUEST')">
<%-- 					    	<fmt:formatDate var="checkIn" pattern="yyyy-MM-dd" value="${searchFormModel.checkIn}" />
							<fmt:formatDate var="checkOut" pattern="yyyy-MM-dd"	value="${searchFormModel.checkOut}" />
							<a href="${baseUrl}/add?roomId=${room.id}&checkin=${checkIn}&checkout=${checkOut}"><mytaglib:i18n key="roomSearch.book" /></a> --%> 			
						<form:form method="POST" action="${pageContext.request.contextPath}/booking/newbooking" modelAttribute="formModel">
								<form:input path="roomId" value="${room.id}" type="hidden" />
								<fmt:formatDate var="checkIn" pattern="yyyy-MM-dd"
									value="${searchFormModel.checkIn}" />
								<fmt:formatDate var="checkOut" pattern="yyyy-MM-dd"
									value="${searchFormModel.checkOut}" />
								<form:input path="checkIn" value="${checkIn}" type="hidden" />
								<form:input path="checkOut" value="${checkOut}" type="hidden" />
								<button class="btn waves-effect waves-light right" type="submit">
									<mytaglib:i18n key="roomSearch.book" />
									<i class="material-icons right">add_shopping_cart</i>
								</button>
							</form:form>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<a href="${pageContext.request.contextPath}/login"><mytaglib:i18n key="roomSearch.book" /></a>
						</sec:authorize>
						<h5 align="right">
							<mytaglib:i18n key="roomPrice" />: <c:out value="${room.actualPrice}" />
						</h5>
					</div>
				</div>
			</div>

		</div>
	</c:forEach>
	<mytags:paging />

</div>
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
					var bookingSearchSlider = document
					.getElementById('booking-search-slider');
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