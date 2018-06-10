<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/photolink" />

<h4 class="header"><mytaglib:i18n key="photolinks" /></h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
            <th><mytaglib:sort-link pageUrl="${baseUrl}" column="room"><mytaglib:i18n key="roomNumber" /></mytaglib:sort-link></th>
            <th><mytaglib:sort-link pageUrl="${baseUrl}" column="userAccount"><mytaglib:i18n key="photolink.userAccount" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="link"><mytaglib:i18n key="link" /></mytaglib:sort-link></th>
			<th><mytaglib:i18n key="photo" /></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="photoLink" items="${listDTO.list}" varStatus="loopCounter">
			<tr>
				<td><c:out value="${photoLink.id}" /></td>
				<td><c:out value="${photoLink.roomNumber}" /></td>
				<td><c:out value="${photoLink.userAccountEmail}" /></td>
				<td><c:out value="${photoLink.link}" /></td>
				<td><img class="materialboxed" width="200px" src="${photoLink.link}"></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${photoLink.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${photoLink.updated}" /></td>
				<td valign="middle">
				    <a class="btn-floating"	href="${baseUrl}/${photoLink.id}">
				           <i class="material-icons">info</i>
				    </a>
				    <a class="btn-floating"	href="${baseUrl}/${photoLink.id}/edit">
				           <i class="material-icons">edit</i>
				    </a>
			        <a class="btn-floating red" href="${baseUrl}/${photoLink.id}/delete">
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