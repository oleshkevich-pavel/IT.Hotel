<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />

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
			<div class="col l6 s12">
				<h5 class="white-text">Footer Content</h5>
				<p class="grey-text text-lighten-4">You can use rows and columns
					here to organize your footer content.</p>
			</div>
			<div class="col l4 offset-l2 s12">
				<h5 class="white-text">Links</h5>
				<ul>
					<li><a class="grey-text text-lighten-3" href="#!">Link 1</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="footer-copyright">
		<div class="container">
			2018 Copyright Text <a class="grey-text text-lighten-4 right"
				href="#!">More Links</a>
		</div>
	</div>
</footer>