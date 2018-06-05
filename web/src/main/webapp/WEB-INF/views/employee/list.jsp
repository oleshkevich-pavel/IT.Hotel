<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- эти 2 таглибы создали мы сами -->
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/employee" />

<h4 class="header">Employees</h4>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="email"><mytaglib:i18n key="email" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="post"><mytaglib:i18n key="post" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="role"><mytaglib:i18n key="role" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="firstName"><mytaglib:i18n key="firstName" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="lastName"><mytaglib:i18n key="lastName" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="birthday"><mytaglib:i18n key="birthday" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="address"><mytaglib:i18n key="address" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="phone"><mytaglib:i18n key="phone" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="hiring"><mytaglib:i18n key="hiring" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="layoff"><mytaglib:i18n key="layoff" /></mytaglib:sort-link></th>
  			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="employee" items="${listDTO.list}" varStatus="loopCounter">
			<tr>
				<td><c:out value="${employee.id}" /></td>
				<td><c:out value="${employee.userAccount.email}" /></td>
				<td><c:out value="${employee.postName}" /></td>
				<td><c:out value="${employee.userAccount.role}" /></td>
				<td><c:out value="${employee.userAccount.firstName}" /></td>
				<td><c:out value="${employee.userAccount.lastName}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${employee.userAccount.birthday}" /></td>
				<td><c:out value="${employee.userAccount.address}" /></td>
				<td><c:out value="${employee.userAccount.phone}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${employee.hiring}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${employee.layoff}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${employee.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd" value="${employee.updated}" /></td>
				<td class="right">
				    <a class="btn-floating"	href="${baseUrl}/${employee.id}">
				           <i class="material-icons">info</i>
				    </a>
				    <a class="btn-floating"	href="${baseUrl}/${employee.id}/edit">
				           <i class="material-icons">edit</i>
				    </a>
			        <a class="btn-floating red"	href="${baseUrl}/${employee.id}/delete">
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