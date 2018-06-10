<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/nouislider.css">
<script
	src="${pageContext.request.contextPath}/resources/js/nouislider.js"></script>

<c:choose>
	<c:when test="${readonly}">
		<h4 class="header">
			<mytaglib:i18n key="bookingStatus.view" />
		</h4>
	</c:when>
	<c:otherwise>
		<h4 class="header">
			<mytaglib:i18n key="bookingStatus.edit" />
		</h4>
	</c:otherwise>
</c:choose>

<c:if test="${'PersistenceException' eq error}">
	<h5 class="header red-text">
		<mytaglib:i18n key="bookingStatus.persistenceException" />
	</h5>
</c:if>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/bookingstatus" />

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="formModel">
		<form:input path="id" type="hidden" />
		<div class="row">
			<div class="input-field col s12">
				<form:input path="name" type="text" disabled="${readonly}" />
				<form:errors path="name" cssClass="red-text" />
				<label for="name"><mytaglib:i18n key="bookingStatus.name" /></label>
			</div>
		</div>
		<div class="row">
			<div class="col s12">
			
				<div class="view">
					<div id="colorpicker">
																
					<div class="col s6">
					<h5>	<div id="rezult"></div></h5>
					
						<div class="sliders" class="noUi-target noUi-ltr noUi-horizontal" id="red"></div><br>
						<div class="sliders" class="noUi-target noUi-ltr noUi-horizontal" id="green"></div><br>
						<div class="sliders" class="noUi-target noUi-ltr noUi-horizontal" id="blue"></div>

						</div>
						
						<div class="col s6">
						<div id="result" style="background: rgb(88, 69, 148); color: rgb(88, 69, 148);"></div>
						</div>

						<form:input path="color" id="color" type="hidden" />
						<form:errors path="color" cssClass="red-text" />
						
					</div>
					<label for="view"><mytaglib:i18n key="bookingStatus.color" /></label>
	</div>
	</div>
				</div>
				<div class="row">
					<div class="col s6"></div>
					<div class="col s3">
						<c:if test="${!readonly}">
							<button class="btn waves-effect waves-light right" type="submit">
								<mytaglib:i18n key="save" />
								<i class="material-icons right">save</i>
							</button>
						</c:if>
					</div>
					<div class="col s3">
						<a class="btn waves-effect waves-light right"
							href="${baseUrl}"><i
							class="material-icons left">reply</i>
						<mytaglib:i18n key="back" /> </a>
					</div>
				</div>
	</form:form>
</div>
<section>


	<script>
		var resultElement = document.getElementById('result');
		var sliders = document.getElementsByClassName('sliders');
	 	/* var colors = [ 0, 0, 0 ];  */
	 	var colors = ${colors};
	 	var color = document.getElementById('color');
	 	
		[].slice.call(sliders).forEach(function(slider, index) {
			
			noUiSlider.create(slider, {
				start : colors[index],
				connect : [ true, false ],
				step : 1,
				range : {
					'min' : 0,
					'max' : 255
				},
				format : wNumb({
					decimals : 0
				})
			});

			// Bind the color changing function to the update event.
			slider.noUiSlider.on('update', function() {

				colors[index] = Math.ceil(slider.noUiSlider.get());

				color.value = 'rgb(' + colors.join(',') + ')';

				resultElement.style.background = color.value;
				resultElement.style.color = color.value;
				document.getElementById('rezult').innerHTML = color.value;
			});
		});
	</script>



</section>