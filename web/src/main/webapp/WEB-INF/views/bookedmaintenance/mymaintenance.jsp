<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="baseUrl"
	value="${pageContext.request.contextPath}/bookedmaintenance/mymaintenance" />

<h4 class="header"><mytaglib:i18n key="myBookings" /></h4>

<table class="bordered highlight">
	<tbody>
		<tr>
<%-- 			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="id"><mytaglib:i18n key="id" /></mytaglib:sort-link></th> --%>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="maintenance"><mytaglib:i18n key="maintenance" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="time"><mytaglib:i18n key="bookedMaintenance.time" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="price"><mytaglib:i18n key="price" /></mytaglib:sort-link></th>
<%-- 			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="created"><mytaglib:i18n key="created" /></mytaglib:sort-link></th>
			<th><mytaglib:sort-link pageUrl="${baseUrl}" column="updated"><mytaglib:i18n key="updated" /></mytaglib:sort-link></th> 
			<th></th>--%>
		</tr>
		<c:forEach var="bookedMaintenance" items="${listDTO.list}"
			varStatus="loopCounter">
			<tr>
<%-- 				<td><c:out value="${booking.id}" /></td> --%>
				<td><c:out value="${bookedMaintenance.maintenanceName}" /></td>
				<td><fmt:formatDate pattern="hh:mm a yyyy-MM-dd" value="${bookedMaintenance.time}" /></td>
				<td><c:out value="${bookedMaintenance.price}" /></td>
<%-- 				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${booking.created}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${booking.updated}" /></td> --%>
<%--				<td class="right">
				    <a class="btn-floating" href="${baseUrl}/${booking.id}"> <i class="material-icons">info</i></a>
 					<a class="btn-floating" href="${baseUrl}/${booking.id}/edit"> <i class="material-icons">edit</i></a>
					<a class="btn-floating red" href="${baseUrl}/${booking.id}/delete"><i class="material-icons">delete</i></a>
					</td>--%>
			</tr>
		</c:forEach>
	</tbody>
</table>
<mytags:paging />
<div class="col s12">
	<a class="btn waves-effect waves-light right" href="${pageContext.request.contextPath}/maintenancesearch"><i class="material-icons left">reply</i><mytaglib:i18n key="back" /></a>
</div>
