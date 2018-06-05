<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/gueststatus" />

<h4 class="header"><mytaglib:i18n key="guestStatuses" /></h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name"><mytaglib:i18n key="guestStatus.name" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="discount"><mytaglib:i18n key="guestStatus.discount" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="gueststatus" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${gueststatus.id}" /></td>
				<td><c:out value="${gueststatus.name}" /></td>
				<td><c:out value="${gueststatus.discount}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${gueststatus.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${gueststatus.updated}" /></td>
				<td class="right">
				    <a class="btn-floating" href="${baseUrl}/${gueststatus.id}">
				           <i class="material-icons">info</i>
				    </a>
				    <a class="btn-floating" href="${baseUrl}/${gueststatus.id}/edit">
				           <i class="material-icons">edit</i>
				    </a>
				    <a class="btn-floating red"	href="${baseUrl}/${gueststatus.id}/delete">
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