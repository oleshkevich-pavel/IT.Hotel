<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/chess.css">

<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<br>
<a class="btn waves-effect waves-light left" href="${baseUrl}/guest/add"
	title="new guest"><mytaglib:i18n key="registration.newGuest" /><i class="material-icons left"></i></a>
<a class="btn waves-effect waves-light left"
	href="${baseUrl}/booking/add" title="new booking"><mytaglib:i18n key="booking.newBooking" /><i
	class="material-icons left"></i></a>
<a class="btn waves-effect waves-light left" href="${baseUrl}/task/add"
	title="new task"><mytaglib:i18n key="task.newTask" /><i class="material-icons left"></i></a>
<br>

<h2><mytaglib:i18n key="calendar" /></h2>

<div class="row">
	<form:form class="col s12" method="POST" action="${baseUrl}/chess"
		modelAttribute="searchFormModel">
		<div class="input-field col s2">
			<form:input path="checkIn" type="text" cssClass="datepicker" />
			<label for="checkIn"><mytaglib:i18n key="day" /></label>
		</div>
		<div class="col s2">
			<button class="btn waves-effect waves-light left" type="submit">
				<mytaglib:i18n key="search" /><i class="material-icons right">search</i>
			</button>
			<%-- <a class="btn waves-effect waves-light left"
				href="javascript:history.back();"><i class="material-icons left">reply</i><mytaglib:i18n key="back" /></a> --%>
		</div>
	</form:form>
</div>


<ul class="pagination">
	<li class="waves-effect"><a class="material-icons" href="?week=-4"><i>fast_rewindt</i></a></li>
	<li class="waves-effect"><a class="material-icons" href="?week=-1"><i>chevron_left</i></a></li>
	<li class="active"><a href="?week=0"><mytaglib:i18n key="today" /></a></li>
	<li class="waves-effect"><a class="material-icons" href="?week=1"><i>chevron_right</i></a></li>
	<li class="waves-effect"><a class="material-icons" href="?week=4"><i>fast_forward</i></a></li>
</ul>

<br>
<div id="chess" class="chess">
	<table>

		<thead>
			<tr>
				<th></th>
				<fmt:formatDate var="prevmonth" value="${calendar[0]}" pattern="MMMM" />
				<c:set var="counter" value="0" />
				<c:forEach var="day" items="${calendar}" varStatus="loopCounter">
					<fmt:formatDate var="month" pattern="MMMM" value="${day}" />
					<c:choose>
						<c:when test="${prevmonth==month}">
							<c:set var="counter" value="${counter+1}" />
						</c:when>
						<c:otherwise>
							<th colspan="${counter}"><c:out value="${prevmonth}"></c:out></th>
							<c:set var="prevmonth" value="${month}" />
							<c:set var="counter" value="0" />
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<th colspan="${counter+1}"><c:out value="${prevmonth}"></c:out></th>
			</tr>
			<tr>
				<th><mytaglib:i18n key="roomNumber" /></th>
				<c:forEach var="day" items="${calendar}" varStatus="loopCounter">
					<th><fmt:formatDate pattern="dd" value="${day}" /><br> <fmt:formatDate
							pattern="E" value="${day}" /></th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:set var="prevfloor" value="0" />
			<c:forEach items="${chess}" var="entry">
				<c:set var="floor" value="${entry.key.floor}" />
				<c:if test="${prevfloor ne floor}">
					<tr class="floor" bgcolor="#cbc8d5">
						<td colspan="${fn:length(calendar)+1}"><c:out
								value="${floor}" /> floor</td>
					</tr>
				</c:if>
				<c:set var="prevfloor" value="${floor}" />
				<tr class="floor${floor}">
					<td class="${entry.key.floor}"><c:out value="${entry.key.number}" /></td>
					<c:forEach var="booking" items="${entry.value}"	varStatus="loopCounter">
						<c:choose>
							<c:when test="${booking==null}">
								<td></td>
							</c:when>
							<c:otherwise>
								<td colspan="${booking.colspan}" style="background-color:${booking.bookingStatusColor}" data-tooltip="<mytaglib:i18n key="guest" />: ${booking.userAccountEmail}<br><mytaglib:i18n key="period" />: ${booking.period}">
										<c:out value="${booking.bookingStatusName}" />
								</td>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div id="tooltip"></div>

<script>
    $("[data-tooltip]").mousemove(function (eventObject) {
        $data_tooltip = $(this).attr("data-tooltip");
        $("#tooltip").html($data_tooltip)
            .css({ 
              "top" : eventObject.pageY + 5,
              "left" : eventObject.pageX + 5
            })
            .show();
        }).mouseout(function () {
          $("#tooltip").hide()
            .html("")
            .css({
                "top" : 0,
                "left" : 0
            });
    });
</script>


<script>
$('.floor').click(function(){
    if($(this).hasClass("collapsed")){
        $(this).nextUntil('tr.floor')
        .find('td')
        .parent()
        .find('td > div')
        .slideDown("fast", function(){
            var $set = $(this);
            $set.replaceWith($set.contents());
        });
        $(this).removeClass("collapsed");
    } else {
        $(this).nextUntil('tr.floor')
        .find('td')
        .wrapInner('<div style="display: block;" />')
        .parent()
        .find('td > div')
        .slideUp("fast");
        $(this).addClass("collapsed");
    }
});
</script>











