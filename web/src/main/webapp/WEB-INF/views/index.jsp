<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<body>
	<br>
	<h1 class="header">IT.Shmotel</h1>
	<div class="slider">
		<ul class="slides">
			<li><img
			    src="${pageContext.request.contextPath}/resources/img/index1.jpg">
				<!--  src="http://www.mansana.com/img/hotels/6/52716.jpg">-->
				<!-- random image -->
				<div class="caption left-align">
					<h3 class="purple-text text-darken-4"><strong><mytaglib:i18n key="index.hotel" />IT.Shmotel</strong></h3>
					<h5 class="light black-text"><mytaglib:i18n key="index.nine" /></h5>
					
				</div></li>
			<li><img
				src="${pageContext.request.contextPath}/resources/img/index2.jpg">
				<!-- src="https://d6.static.media.condenast.ru/allure/gallery_cover/4338127140f023e8eaccdfb92035e7bd.jpg/bc2bb4b7/o/w800"> -->
				<!-- random image -->
				<div class="caption left-align">
					<h3><mytaglib:i18n key="index.ten" /></h3>
					<h5 class="light blue-text text-darken-4"><mytaglib:i18n key="index.eleven" /></h5>
				</div></li>
			<li><img
				src="${pageContext.request.contextPath}/resources/img/index3.jpg"> 
				<%-- src="http://desktopwallpapers.org.ua/pic/201111/1024x768/desktopwallpapers.org.ua-8450.jpg">--%>
				<!-- random image -->
				<div class="caption left-align">
					<h3><strong><mytaglib:i18n key="index.twelve" /></strong></h3>
				</div></li>	
			<li><img
				src="${pageContext.request.contextPath}/resources/img/index4.jpg">
				<!-- src="https://www-images.christianitytoday.com/images/72184.jpg?w=1240"> -->
				<!-- random image -->
				<div class="caption left-align">
					<h3><mytaglib:i18n key="index.thirteen" /></h3>
				</div></li>
		</ul>
	</div>
		<p><h5>
			<mytaglib:i18n key="index.one" />
			<a href="${pageContext.request.contextPath}/roomsearch/"><mytaglib:i18n	key="index.two" /></a>
			<mytaglib:i18n key="index.three" />
			</h5>
		
		<p><h5>
			<mytaglib:i18n key="index.four" />
			<a href="${pageContext.request.contextPath}/registration/"><mytaglib:i18n
					key="index.fife" /></a>
			<mytaglib:i18n key="index.six" />
			<a href="${pageContext.request.contextPath}/maintenance/"><mytaglib:i18n
					key="index.seven" /></a>
		</h5>
		<p><h5>
			<mytaglib:i18n key="index.eight" />
	</h5>
</body>