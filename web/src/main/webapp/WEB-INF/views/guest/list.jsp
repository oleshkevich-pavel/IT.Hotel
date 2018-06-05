<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/guest" />

<h4 class="header"><mytaglib:i18n key="guests" /></h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="email"><mytaglib:i18n key="email" /></mytaglib:sort-link></th>
<%-- 			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="password">password</mytaglib:sort-link></th>--%>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="firstName"><mytaglib:i18n key="firstName" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="lastName"><mytaglib:i18n key="lastName" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="birthday"><mytaglib:i18n key="birthday" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="address"><mytaglib:i18n key="address" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="phone"><mytaglib:i18n key="phone" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="verifyKey"><mytaglib:i18n key="guest.verifyKey" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="verified"><mytaglib:i18n key="guest.isEmailVerified" /></mytaglib:sort-link></th>
            <th><mytaglib:sort-link pageUrl="${baseUrl}" column="guestStatus"><mytaglib:i18n key="guestStatus" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="credit"><mytaglib:i18n key="credit" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="guest" items="${listDTO.list}" varStatus="loopCounter">
			<tr>
				<td><c:out value="${guest.id}" /></td>
				<td><c:out value="${guest.userAccount.email}" /></td>
<%-- 				<td><c:out value="${guest.userAccount.password}" /></td>--%>
				<td><c:out value="${guest.userAccount.firstName}" /></td>
				<td><c:out value="${guest.userAccount.lastName}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${guest.userAccount.birthday}" /></td>
				<td><c:out value="${guest.userAccount.address}" /></td>
				<td><c:out value="${guest.userAccount.phone}" /></td>
				<td><c:out value="${guest.verifyKey}" /></td>
				<td><c:out value="${guest.verified}" /></td>  
				<td><c:out value="${guest.guestStatusName}" /></td>
				<td><c:out value="${guest.credit}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${guest.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${guest.updated}" /></td>
				<td class="right">
				    <a class="btn-floating"	href="${baseUrl}/${guest.id}">
				           <i class="material-icons">info</i>
				    </a>
				    <a class="btn-floating"	href="${baseUrl}/${guest.id}/edit">
				           <i class="material-icons">edit</i>
				    </a>
			        <a class="btn-floating red"	href="${baseUrl}/${guest.id}/delete">
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