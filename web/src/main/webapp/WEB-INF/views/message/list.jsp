<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/message" />

<h4 class="header">
	<mytaglib:i18n key="messages" />
</h4>

<c:if test="${not empty error}">
	<div class="row">
		<div class="col s12 center">
			<div class="error">${error}</div>
		</div>
	</div>
</c:if>

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}"
		modelAttribute="searchFormModel">
		<div class="row">
			<div class="input-field col s12">
				<form:input path="name" type="text" />
				<label for="name"><mytaglib:i18n key="message.name" /></label>
			</div>
		</div>
		<div class="row">
			<div class="input-field col s12">
				<form:input path="phone" type="text" />
				<label for="phone"><mytaglib:i18n key="phone" /></label>
			</div>
		</div>

		<div class="row">
			<div class="input-field col s12">
				<form:select path="email">
					<option value="" selected><mytaglib:i18n key="any" /></option>
					<form:options items="${emailChoises}" />
				</form:select>
				<form:errors path="email" cssClass="red-text" />
				<label for="email"><mytaglib:i18n key="email" /></label>
			</div>
		</div>
		<div class="col s12">
			<button class="btn waves-effect waves-light right" type="submit">
				<mytaglib:i18n key="search" />
				<i class="material-icons right">search</i>
			</button>
		</div>
	</form:form>
</div>

<table class="bordered highlight">
	<tbody>
		<tr>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id">
					<mytaglib:i18n key="id" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="name">
					<mytaglib:i18n key="message.name" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="phone">
					<mytaglib:i18n key="phone" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="email">
					<mytaglib:i18n key="email" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="message">
					<mytaglib:i18n key="message" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created">
					<mytaglib:i18n key="created" />
				</mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated">
					<mytaglib:i18n key="updated" />
				</mytaglib:sort-link></th>
			<th></th>
		</tr>
		<c:forEach var="message" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
				<td><c:out value="${message.id}" /></td>
				<td><c:out value="${message.name}" /></td>
				<td><c:out value="${message.phone}" /></td>
				<td><c:out value="${message.email}" /></td>
				<td><c:out value="${message.message}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${message.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${message.updated}" /></td>
				<td class="right"><a class="btn-floating"
					href="${baseUrl}/${message.id}"> <i class="material-icons">info</i>
				</a> <a class="btn-floating" href="${baseUrl}/${message.id}/edit"> <i
						class="material-icons">edit</i>
				</a> <a class="btn-floating red" href="${baseUrl}/${message.id}/delete">
						<i class="material-icons">delete</i>
				</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<mytags:paging />
<a class="waves-effect waves-light btn right" href="${baseUrl}/add">
	<i class="material-icons">add</i>
</a>