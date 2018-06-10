<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<footer class="page-footer">
<%-- 	<div class="fixed-action-btn">
		<a class="btn-floating btn-large red"> <i
			class="large material-icons">translate</i>
		</a>
		<ul>
			<li><a class="btn-floating green" href="${baseUrl}?language=ru">RU</a></li>
			<li><a class="btn-floating blue" href="${baseUrl}?language=en">EN</a></li>
		</ul>
	</div> --%>
	<div class="container">
		<div class="row">
			<div class="col l2 s6">
				<h5 class="white-text"><mytaglib:i18n key="footer.availableUsers" /></h5>
				<ul>
					<li><p class="grey-text text-lighten-4">guest@tut.by</p></li>
					<li><p class="grey-text text-lighten-4">guestVIP@tut.by</p></li>
					<li><p class="grey-text text-lighten-4">employee@tut.by</p></li>
					<li><p class="grey-text text-lighten-4">reseption@tut.by</p></li>
					<li><p class="grey-text text-lighten-4">admin@tut.by</p></li>
					</ul>
			</div>
			<div class="col l2 s6">
			<h5 class="white-text"><mytaglib:i18n key="footer.passwords" /></h5>
				<ul>
					<li><p class="grey-text text-lighten-4">guest</p></li>
					<li><p class="grey-text text-lighten-4">guestVIP</p></li>
					<li><p class="grey-text text-lighten-4">employee</p></li>
					<li><p class="grey-text text-lighten-4">reseption</p></li>
					<li><p class="grey-text text-lighten-4">admin</p></li>
					</ul>
			</div>
			<div class="col l3 offset-l5 s12">
				<h5 class="white-text"><mytaglib:i18n key="footer.links" /></h5>
				<ul>
					<li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/resources/img/db-scheme.png" target="_blank">DB scheme</a></li>
					<li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/resources/img/mindmup.png" target="_blank">mindmup</a></li>
					<li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/resources/img/jmeter/jmeter1.png" target="_blank">jmeter1</a></li>
					<li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/resources/img/jmeter/jmeter2.png" target="_blank">jmeter2</a></li>
					<li><a class="grey-text text-lighten-3" href="${pageContext.request.contextPath}/resources/img/jmeter/jmeter3.png" target="_blank">jmeter3</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="footer-copyright">
		<div class="container">
			2018 Copyright IT.Shmotel <!-- <a class="grey-text text-lighten-4 right"
				href="#!">More Links</a> -->
		</div>
	</div>
</footer>