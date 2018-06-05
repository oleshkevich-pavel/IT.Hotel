<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/useraccount" />

<h4 class="header"><mytaglib:i18n key="userAccounts" /></h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="email"><mytaglib:i18n key="email" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="role"><mytaglib:i18n key="role" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="firstName"><mytaglib:i18n key="firstName" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="lastName"><mytaglib:i18n key="lastName" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="birthday"><mytaglib:i18n key="birthday" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="address"><mytaglib:i18n key="address" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="phone"><mytaglib:i18n key="phone" /></mytaglib:sort-link></th>
  			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="useraccount" items="${listDTO.list}" varStatus="loopCounter">
			<tr>
				<td><c:out value="${useraccount.id}" /></td>
				<td><c:out value="${useraccount.email}" /></td>
				<td><c:out value="${useraccount.role}" /></td>
				<td><c:out value="${useraccount.firstName}" /></td>
				<td><c:out value="${useraccount.lastName}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${useraccount.birthday}" /></td>
				<td><c:out value="${useraccount.address}" /></td>
				<td><c:out value="${useraccount.phone}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${useraccount.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${useraccount.updated}" /></td>
				<td class="right">
				    <a class="btn-floating"	href="${baseUrl}/${useraccount.id}">
				           <i class="material-icons">info</i>
				    </a>
				    <a class="btn-floating"	href="${baseUrl}/${useraccount.id}/edit">
				           <i class="material-icons">edit</i>
				    </a>
			        <a class="btn-floating red disabled" href="${baseUrl}/${useraccount.id}/delete">
		                   <i class="material-icons">delete</i>
			        </a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<mytags:paging />
<a class="waves-effect waves-light btn right disabled" href="${baseUrl}/add">
    <i class="material-icons">add</i>
</a>