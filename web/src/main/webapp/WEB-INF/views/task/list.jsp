<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/task" />

<h4 class="header"><mytaglib:i18n key="tasks" /></h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="toDo"><mytaglib:i18n key="task.toDo" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="description"><mytaglib:i18n key="description" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="executionTime"><mytaglib:i18n key="task.executionTime" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="answerable"><mytaglib:i18n key="task.answerable" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="priority"><mytaglib:i18n key="task.priority" /></mytaglib:sort-link></th>
            <th><mytaglib:sort-link pageUrl="${baseUrl}" column="creator"><mytaglib:i18n key="task.creator" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="executed"><mytaglib:i18n key="task.executed" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="task" items="${listDTO.list}" varStatus="loopCounter">
			<tr>
				<td><c:out value="${task.id}" /></td>
				<td><c:out value="${task.toDo}" /></td>
				<td><c:out value="${task.description}" /></td>
				<td><fmt:formatDate pattern="hh:mm a // yyyy-MM-dd" value="${task.executionTime}" /></td>
				<td><c:out value="${task.answerableEmail}" /></td>
				<td><c:out value="${task.priority}" /></td>
				<td><c:out value="${task.creatorEmail}" /></td>
				<td><c:out value="${task.executed}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${task.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${task.updated}" /></td>
				<td class="right">
				    <a class="btn-floating"	href="${baseUrl}/${task.id}">
				           <i class="material-icons">info</i>
				    </a>
				    <a class="btn-floating"	href="${baseUrl}/${task.id}/edit">
				           <i class="material-icons">edit</i>
				    </a>
			        <a class="btn-floating red"	href="${baseUrl}/${task.id}/delete">
		                   <i class="material-icons">delete</i>
			        </a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<mytags:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add">
    <i class="material-icons">add</i>
</a>