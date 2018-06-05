<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/maintenance" />

<h4 class="header"><mytaglib:i18n key="maintenances" /></h4>

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="input-field col s3">
			<form:input path="name" type="text" />
			<label for="name"><mytaglib:i18n key="maintenance.name" /></label>
		</div>
		<div class="input-field col s3">
			<div class="switch">
				<label><mytaglib:i18n key="all" /><form:checkbox path="available" /> <span
					class="lever"></span><mytaglib:i18n key="maintenance.available" />
				</label>
			</div>
			<label class="switch-label"><mytaglib:i18n key="maintenance.availability" /></label> <br />
		</div>
		<div class="col s3">
			<button class="btn waves-effect waves-light right" type="submit">
				<mytaglib:i18n key="search" /><i class="material-icons right">search</i>
			</button>
		</div>
		<div class="col s3"></div>
	</form:form>
</div>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name"><mytaglib:i18n key="maintenance.name" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="actualPrice"><mytaglib:i18n key="actualPrice" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="available"><mytaglib:i18n key="maintenance.availability" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="maintenance" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${maintenance.id}" /></td>
				<td><c:out value="${maintenance.name}" /></td>
				<td><c:out value="${maintenance.actualPrice}" /></td>
				<td><c:out value="${maintenance.available}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${maintenance.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${maintenance.updated}" /></td>
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${maintenance.id}"> <i class="material-icons">info</i>
				</a> <a class="btn-floating" href="${baseUrl}/${maintenance.id}/edit">
						<i class="material-icons">edit</i>
				</a> <a class="btn-floating red"
					href="${baseUrl}/${maintenance.id}/delete"> <i
						class="material-icons">delete</i>
				</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<mytags:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add">
	<i class="material-icons">add</i>
</a>