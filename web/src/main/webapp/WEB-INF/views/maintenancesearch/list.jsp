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
	value="${pageContext.request.contextPath}/maintenancesearch" />

<div class="col s3">
		<div class="fix_block"> 

	<form:form method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="row">
			<div class="input-field col s12">
				<form:select path="id">
					<option value="" selected><mytaglib:i18n key="any" /></option>
					<form:options items="${maintenanceChoices}" />
				</form:select>
				<form:errors path="id" cssClass="red-text" />
				<label for="id"><mytaglib:i18n key="chooseMaintenance" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<div id="maintenance-search-slider"
					class="noUi-target noUi-ltr noUi-horizontal"></div>
				<label for="maintenance-search"><mytaglib:i18n
						key="priceRange" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s6">
				<form:input path="priceMin" type="number" id="input-price-min"
					disabled="${slider.valueMax==0}" />
				<label for="priceMin"><mytaglib:i18n key="priceMin" /></label>
			</div>
			<div class="input-field col s6">
				<form:input path="priceMax" type="number" id="input-price-max"
					disabled="${slider.valueMax==0}" />
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
</div>
<div class="col s9">
	<c:if test="${empty listDTO.list}">
		<h4>
			<mytaglib:i18n key="maintenanceSearch.noMaintenancesFound" />
		</h4>
	</c:if>
	<mytags:paging />
	<c:forEach var="maintenance" items="${listDTO.list}">
		<div class="row">
			<div class="col s12">
				<div class="card">
					<div class="card-image">
						<div class="slider">
							<ul class="slides">
								<c:choose>
									<c:when test="${empty maintenance.photoLink}">
										<div class="caption center-align">
											<h3>
												<mytaglib:i18n key="maintenanceSearch.noPhotos" />
												<c:out value="${maintenance.name}" />
											</h3>
										</div>
									</c:when>
									<c:otherwise>
										<li><img class="materialboxed" src="${maintenance.photoLink}">
										<%-- <span class="card-title"><c:out value="${maintenance.name}" /></span> --%></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
					<div class="card-content">
						<p>
							<c:out value="${maintenance.name}" />
						</p>
					</div>
					<div class="card-action">
						<sec:authorize access="hasRole('ROLE_GUEST')">
							<a href="${pageContext.request.contextPath}/bookedmaintenance/newmaintenance/${maintenance.id}"> <mytaglib:i18n
									key="maintenanceSearch.newOrder" /></a>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<a href="${pageContext.request.contextPath}/login"><mytaglib:i18n
									key="maintenanceSearch.newOrder" /></a>
						</sec:authorize>
						<h5 align="right">
							<mytaglib:i18n key="price" />
							:
							<c:out value="${maintenance.actualPrice}" />
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
			maintenanceSearchSlider.noUiSlider.on('update',
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
					var maintenanceSearchSlider = document
					.getElementById('maintenance-search-slider');
			noUiSlider.create(maintenanceSearchSlider, {
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
			maintenanceSearchSlider.noUiSlider.on('update',
					function(values, handle) {
						var value = values[handle];
						if (handle) {
							valueMax.value = value;
						} else {
							valueMin.value = value;
						}
					});
			priceMin.addEventListener('change', function() {
				maintenanceSearchSlider.noUiSlider.set([ this.value, null ]);
			});
			priceMax.addEventListener('change', function() {
				maintenanceSearchSlider.noUiSlider.set([ null, this.value ]);
			});
		</script>