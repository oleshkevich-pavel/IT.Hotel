<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}" />

<nav class="nav-extended">
	<div class="nav-wrapper">
		<div class="row">
			<div class="col s9">
				<div class="right">
					<sec:authorize access="!isAnonymous()">
						<ul id="dropdown2" class="dropdown-content">
							<sec:authorize access="hasRole('ROLE_GUEST')">
								<li><a href="${baseUrl}/booking/mybooking"><mytaglib:i18n key="myBookings" /></a></li>
								<li><a href="${baseUrl}/bookedmaintenance/mymaintenance"><mytaglib:i18n key="myMaintenance" /></a></li>
								<li class="divider"></li>
								<li><a href="${baseUrl}/guest/myprofile"><mytaglib:i18n	key="header.editProfile" /></a></li>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEPTION', 'ROLE_EMPLOYEE')">
								<li><a href="${baseUrl}/todaytask"><mytaglib:i18n key="todayTasks" /></a></li>
							</sec:authorize>
							<li class="divider"></li>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEPTION')">
									<li><a href="${baseUrl}/upload"><mytaglib:i18n key="header.fileUploading" /></a></li>
							</sec:authorize>
							<li class="divider"></li>
							<li><a href="${baseUrl}/execute_logout"><mytaglib:i18n key="logOut" /></a></li>
						</ul>
						<ul class="right">
							<!-- Dropdown Trigger -->
							<li><a class="dropdown-trigger" href="#!" data-target="dropdown2">
							         <sec:authentication property="principal" />
							         <sec:authentication property="authorities" />
							         <i class="material-icons right">arrow_drop_down</i>
							</a></li>
						</ul>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<a href="${baseUrl}/registration"><mytaglib:i18n key="registration" /></a>
						<!-- Modal Trigger -->
						<a class="waves-effect waves-light btn-small modal-trigger"
							href="#modal1"><mytaglib:i18n key="logIn" /></a>
						<%-- <a class="waves-effect waves-light btn modal-trigger" href="${baseUrl}/login">Войти</a> --%>
						<!-- Modal Structure -->
						<div id="modal1" class="modal">
							<form name='loginForm' action="${baseUrl}/login" method='POST'>
								<div class="modal-content">
									<div class="row">
										<div class="col s1"></div>
										<div class="col s10 center">
											<div class="row">
												<div class="input-field col s12">
													<input id="email" type="email" class="validate"
														name='email' value=''> <label for="email"><mytaglib:i18n
															key="email" /></label>
												</div>
											</div>
											<div class="row">
												<div class="input-field col s12">
													<input id="password" type="password" class="validate"
														name='password'> <label for="password"><mytaglib:i18n
															key="password" /></label>
												</div>
											</div>
											<div class="row">
												<div class="col s12">
													<button class="btn-small waves-effect waves-light"
														type="submit"><mytaglib:i18n key="logIn" /></button>
												</div>
											</div>
										</div>
										<div class="col s1"></div>
									</div>
								</div>
							</form>
						</div>
					</sec:authorize>
				</div>
			</div>
			<div class="col s3">
				<div class="right">
					<span id="clock"></span>
				</div>
			</div>
		</div>
	</div>
	<div class="nav-content">
		<div class="row">
			<div class="col s2 center">
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<ul id="dropdown1" class="dropdown-content">
						<li><a href="${baseUrl}/guest"><mytaglib:i18n key="guests" /></a></li>
						<li><a href="${baseUrl}/employee"><mytaglib:i18n key="employees" /></a></li>
						<li><a href="${baseUrl}/post"><mytaglib:i18n key="posts" /></a></li>
						<li><a href="${baseUrl}/task"><mytaglib:i18n key="tasks" /></a></li>
						<li class="divider"></li>
						<li><a href="${baseUrl}/maintenance"><mytaglib:i18n key="maintenances" /></a></li>
						<li><a href="${baseUrl}/bookedmaintenance"><mytaglib:i18n key="bookedMaintenances" /></a></li>
						<li><a href="${baseUrl}/booking"><mytaglib:i18n key="bookings" /></a></li>
						<li><a href="${baseUrl}/room"><mytaglib:i18n key="rooms" /></a></li>
						<li class="divider"></li>
						<li><a href="${baseUrl}/bookingstatus"><mytaglib:i18n key="bookingStatuses" /></a></li>
						<li><a href="${baseUrl}/gueststatus"><mytaglib:i18n key="guestStatuses" /></a></li>
						<li class="divider"></li>
						<%-- 						<li><a href="${baseUrl}/useraccount"><mytaglib:i18n key="userAccounts" /></a></li> --%>
						<li><a href="${baseUrl}/photolink"><mytaglib:i18n key="photolinks" /></a></li>
						<li><a href="${baseUrl}/comment"><mytaglib:i18n key="comments" /></a></li>
						<li><a href="${baseUrl}/message"><mytaglib:i18n key="messages" /></a></li>
					</ul>
					<ul class="right">
						<li><a class="dropdown-trigger" href="#!" data-target="dropdown1"><mytaglib:i18n key="header.dbMenu" /><i
								class="material-icons right">arrow_drop_down</i></a></li>
					</ul>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_RESEPTION')">
					<ul id="dropdown1" class="dropdown-content">
						<li><a href="${baseUrl}/guest"><mytaglib:i18n key="guests" /></a></li>
						<li><a href="${baseUrl}/bookedmaintenance"><mytaglib:i18n key="bookedMaintenances" /></a></li>
						<li><a href="${baseUrl}/booking"><mytaglib:i18n key="bookings" /></a></li>
						<li class="divider"></li>
						<li><a href="${baseUrl}/task"><mytaglib:i18n key="tasks" /></a></li>
						<li class="divider"></li>
						<li><a href="${baseUrl}/message"><mytaglib:i18n key="messages" /></a></li>
					</ul>
					<ul class="right">
						<li><a class="dropdown-trigger" href="#!" data-target="dropdown1"><mytaglib:i18n key="header.dbMenu" /><i
								class="material-icons right">arrow_drop_down</i></a></li>
					</ul>
				</sec:authorize>
				<sec:authorize access="!hasAnyRole('ROLE_ADMIN', 'ROLE_RESEPTION')">
					<a href="${baseUrl}/"><mytaglib:i18n key="homepage" /></a>
				</sec:authorize>
			</div>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEPTION')">
				<div class="col s2 center">
					<a href="${baseUrl}/chess"><mytaglib:i18n key="chess" /></a>
				</div>
			</sec:authorize>
		<%-- 	<sec:authorize access="!hasRole('ROLE_EMPLOYEE') "> --%>
				<div class="col s2 center">
					<a href="${baseUrl}/roomsearch"><mytaglib:i18n key="roomSearch" /></a>
				</div>
				<div class="col s2 center">
					<a href="${baseUrl}/maintenancesearch"><mytaglib:i18n key="maintenanceSearch" /></a>
				</div>
		<%-- 	</sec:authorize> --%>
			<div class="col s2 center">
				<a href="${baseUrl}/gallery"><mytaglib:i18n key="gallery" /></a>
			</div>

			<sec:authorize access="!hasAnyRole('ROLE_ADMIN', 'ROLE_RESEPTION')">
				<div class="col s2 center">
					<a href="${baseUrl}/contacts"><mytaglib:i18n key="contacts" /></a>
				</div>
			</sec:authorize>

			<div class="col s2 center">
				<a class="waves-effect waves-light btn"	href="${baseUrl}?language=ru">RU</a>
				<a class="waves-effect waves-light btn" href="${baseUrl}?language=en">EN</a>
			</div>
		</div>
	</div>
</nav>

<%-- <c:set var="language" value="${sessionScope.current-locale.language}" />
<c:if test="${language eq 'RU' }"><c:out value="${language}"></c:out></c:if> --%>

<script type="text/javascript">
	obj_hours = document.getElementById("clock");

	name_month = new Array("января", "февраля", "марта", "апреля", "мая",
			"июня", "июля", "августа", "сентября", "октября", "ноября",
			"декабря");
	name_day = new Array("ВС", "ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ");

	function wr_hours() {
		time = new Date();

		time_sec = time.getSeconds();
		time_min = time.getMinutes();
		time_hours = time.getHours();
		time_wr = ((time_hours < 10) ? "0" : "") + time_hours;
		time_wr += ":";
		time_wr += ((time_min < 10) ? "0" : "") + time_min;
		time_wr += ":";
		time_wr += ((time_sec < 10) ? "0" : "") + time_sec;

		time_wr = name_day[time.getDay()] + ", " + time.getDate() + " "
				+ name_month[time.getMonth()] + " " + time.getFullYear() + ", "
				+ time_wr;

		obj_hours.innerHTML = time_wr;
	}

	wr_hours();
	setInterval("wr_hours();", 1000);
</script>


