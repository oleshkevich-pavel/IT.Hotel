<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/contacts" />

<div class="row">

	<div class="col s6">
		<h3>
			<mytaglib:i18n key="contacts" />
		</h3>
		<h5>
			<p>
				<strong><mytaglib:i18n key="address" />:</strong><br>
				<mytaglib:i18n key="contacts.streetAddress" />
				<br>
				<mytaglib:i18n key="contacts.cityAddress" />
				<br>
				<mytaglib:i18n key="belarus" />
			</p>
			<p>
				<strong><mytaglib:i18n key="contacts.phone" />:</strong>+375 29 266
				33 04<br>
			</p>
			<p>
				<strong><mytaglib:i18n key="email" />:</strong>
				it.shmotel@gmail.com
			</p>
		</h5>
	</div>

	<form:form class="col s6" method="POST" action="${baseUrl}"
		modelAttribute="formModel">

		<c:if test="${not empty error}">
			<div class="row">
				<div class="col s12 center">
					<div class="error">${error}</div>
				</div>
			</div>
		</c:if>

		<div class="row">
			<div class="input-field col s12">
				<form:input path="name" type="text" />
				<form:errors path="name" cssClass="red-text" />
				<label for="name"><mytaglib:i18n key="firstName" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="phone" type="text" />
				<form:errors path="phone" cssClass="red-text" />
				<label for="phone"><mytaglib:i18n key="phone" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="email" type="email" />
				<form:errors path="email" cssClass="red-text" />
				<label for="email"><mytaglib:i18n key="email" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:textarea path="message" type="text"
					class="materialize-textarea" />
				<form:errors path="message" cssClass="red-text" />
				<label for="message"><mytaglib:i18n
						key="contacts.yourMessage" /></label>
			</div>
		</div>
		<div class="row">
			<div class="col s6">
				<div class="g-recaptcha"
					data-sitekey="6LcjfVoUAAAAANHUeelMXznTDWOMpjVUS6x3sWBg"></div>
			</div>
			<div class="col s6">
				<button class="btn waves-effect waves-light right" type="submit"
					value="Submit">
					<mytaglib:i18n key="send" />
					<i class="material-icons right">send</i>
				</button>
			</div>
		</div>
	</form:form>
</div>
<div class="row">
	<script type="text/javascript" charset="utf-8" async
		src="https://api-maps.yandex.ru/services/constructor/1.0/js/?um=constructor%3Afee54ce363d2f19b47237de25049d5e1dad92eeec3ae5002d32c3f2a0dc8d3eb&amp;width=100%25&amp;height=300&amp;lang=ru_RU&amp;scroll=true"></script>
</div>