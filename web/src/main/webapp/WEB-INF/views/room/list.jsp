<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/room" />

<h4 class="header"><mytaglib:i18n key="rooms" /></h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="number"><mytaglib:i18n key="roomNumber" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="floor"><mytaglib:i18n key="floor" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="type"><mytaglib:i18n key="roomType" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="accomodation"><mytaglib:i18n key="accomodation" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="view"><mytaglib:i18n key="view" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="actualPrice"><mytaglib:i18n key="actualPrice" /></mytaglib:sort-link></th>
            <th><mytaglib:sort-link pageUrl="${baseUrl}" column="description"><mytaglib:i18n key="description" /></mytaglib:sort-link></th>
            <th><mytaglib:sort-link pageUrl="${baseUrl}" column="dirty"><mytaglib:i18n key="room.getDirty" /></mytaglib:sort-link></th>
            <th><mytaglib:sort-link pageUrl="${baseUrl}" column="broken"><mytaglib:i18n key="room.repair" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="room" items="${listDTO.list}" varStatus="loopCounter">
			<tr>
				<td><c:out value="${room.id}" /></td>
				<td><c:out value="${room.number}" /></td>
				<td><c:out value="${room.floor}" /></td>
				<td><c:out value="${room.type}" /></td>
				<td><c:out value="${room.accomodation}" /></td>
				<td><c:out value="${room.view}" /></td>
				<td><c:out value="${room.actualPrice}" /></td>
				<td><c:out value="${room.description}" /></td>
				<td><c:out value="${room.dirty}" /></td>
				<td><c:out value="${room.broken}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${room.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${room.updated}" /></td>
				<td class="right">
				    <a class="btn-floating"	href="${baseUrl}/${room.id}">
				           <i class="material-icons">info</i>
				    </a>
				    <a class="btn-floating"	href="${baseUrl}/${room.id}/edit">
				           <i class="material-icons">edit</i>
				    </a>
			        <a class="btn-floating red"	href="${baseUrl}/${room.id}/delete">
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