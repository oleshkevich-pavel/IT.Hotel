<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/todaytask" />



<ul id="tabs-swipe-demo" class="tabs">
	<li class="tab col s4"><a class="active" href="#test-swipe-1">
			<mytaglib:i18n key="todayTasks" />
	</a></li>
	<li class="tab col s4"><a href="#test-swipe-2"><mytaglib:i18n
							key="todayTask.dirtyRooms" /></a></li>
	<li class="tab col s4"><a href="#test-swipe-3"><mytaglib:i18n
							key="todayTask.brokenRooms" /></a></li>
</ul>

<div id="test-swipe-1" class="col s12">
	<div class="row">
		<form:form class="col s6" method="POST" action="${baseUrl}"
			modelAttribute="searchFormModel">
			<div class="row">
				<div class="input-field col s6">
					<form:input path="executionDate" type="text" cssClass="datepicker" />
					<label for="executionDate"><mytaglib:i18n
							key="task.executionDate" /></label>
				</div>
				<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_RESEPTION')">
					<div class="input-field col s6">
						<div class="switch">
							<label><mytaglib:i18n key="all" /> <form:checkbox
									path="executed" /> <span class="lever"></span> <mytaglib:i18n
									key="todayTask.open" /> </label>
						</div>
						<!-- 	<label class="switch-label">executed</label> <br /> -->
					</div>
			</div>
			<div class="row">
				<div class="input-field col s6">
					<form:select path="answerableId">
						<option value="" selected><mytaglib:i18n key="any" /></option>
						<form:options items="${answerableChoices}" />
					</form:select>
					<form:errors path="answerableId" cssClass="red-text" />
					<label for="answerableId"><mytaglib:i18n
							key="task.answerable" /></label>
				</div>
				</sec:authorize>
				<div class="col s6">
					<button class="btn waves-effect waves-light left" type="submit">
						<mytaglib:i18n key="search" />
					</button>
				</div>
			</div>
		</form:form>
	</div>
<c:choose>
<c:when test="${listDTO.totalCount eq 0}"><h4 class="header"><mytaglib:i18n key="noTodayTasks" /></h4></c:when>
<c:otherwise>
	<table class="bordered highlight">
		<tbody>
			<tr>
				<th><mytaglib:sort-link pageUrl="${baseUrl}" column="toDo">
						<mytaglib:i18n key="task.toDo" />
					</mytaglib:sort-link></th>
				<th><mytaglib:sort-link pageUrl="${baseUrl}"
						column="description">
						<mytaglib:i18n key="description" />
					</mytaglib:sort-link></th>
				<th><mytaglib:sort-link pageUrl="${baseUrl}"
						column="executionTime">
						<mytaglib:i18n key="task.executionTime" />
					</mytaglib:sort-link></th>
				<th><mytaglib:sort-link pageUrl="${baseUrl}" column="priority">
						<mytaglib:i18n key="task.priority" />
					</mytaglib:sort-link></th>
				<th><mytaglib:sort-link pageUrl="${baseUrl}" column="creator">
						<mytaglib:i18n key="task.creator" />
					</mytaglib:sort-link></th>
				<th></th>
			</tr>
			<c:forEach var="task" items="${listDTO.list}" varStatus="loopCounter">
				<tr>
					<td><c:out value="${task.toDo}" /></td>
					<td><c:out value="${task.description}" /></td>
					<td><fmt:formatDate pattern="hh:mm a // yyyy-MM-dd"
							value="${task.executionTime}" /></td>
					<td><c:out value="${task.priority}" /></td>
					<td><c:out value="${task.creatorEmail}" /></td>
					<td class="right"><a class="btn-floating"
						href="${pageContext.request.contextPath}/task/${task.id}"> <i
							class="material-icons">info</i>
					</a> <c:choose>
							<c:when test="${task.executed}">
								<a class="waves-effect waves-light btn" disabled="true"><i
									class="material-icons right">done_all</i> <mytaglib:i18n
										key="todayTask.done" /></a>
							</c:when>

							<c:otherwise>
								<a class="waves-effect waves-light btn tooltipped"
									href="${baseUrl}/${task.id}/done" data-position="bottom"
					data-tooltip="Выполнить"><i
									class="material-icons right">done</i> <mytaglib:i18n
										key="todayTask.execute" /></a>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<mytags:paging />
	</c:otherwise>
</c:choose>
</div>

<div id="test-swipe-2" class="col s12">
	<div class="row">
		<c:forEach var="room" items="${dirtyRooms.list}"
			varStatus="loopCounter">
			<div class="col s1">
				<br> 
				<a class="waves-effect waves-light btn tooltipped" href="${baseUrl}/${room.id}/clean" data-position="bottom"
					data-tooltip="Убрать"><c:out value="${room.number}" /><i class="material-icons left">pan_tool</i></a>
			</div>
		</c:forEach>
	</div>
</div>

<div id="test-swipe-3" class="col s12">
	<div class="row">
		<c:forEach var="room" items="${brokenRooms.list}"
			varStatus="loopCounter">
			<div class="col s1">
				<br> <a class="waves-effect waves-light btn tooltipped" href="${baseUrl}/${room.id}/repair" data-position="bottom"
					data-tooltip="Отремонтировать"><c:out value="${room.number}" /><i class="material-icons left">build</i></a>
			</div>
		</c:forEach>
	</div>
</div>