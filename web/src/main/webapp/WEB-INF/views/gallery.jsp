<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/gallery" />

<h4 class="header">
	<mytaglib:i18n key="gallery" />
</h4>

<ul class="collapsible popout">
	<c:set var="active" value="true" />
	<c:forEach var="entry" items="${roomsWithPhotoLinks}"
		varStatus="loopCounter">

		<c:set var="room" value="${entry.key}" />
		<c:choose>
			<c:when test="${active==true}">
				<li class="active"><c:set var="active" value="false" />
			</c:when>
			<c:otherwise>
				<li>
			</c:otherwise>
		</c:choose>
		<div class="collapsible-header">
			<div class="col s3">
				<strong> <mytaglib:i18n key="room" /> <c:out
						value="${room.number}" />
				</strong>
			</div>
			<div class="col s3">
				<mytaglib:i18n key="floor" />
				<c:out value="${room.floor}" />
			</div>
			<div class="col s3">
				<mytaglib:i18n key="price" />
				<c:out value="${room.actualPrice}" />
			</div>
			<div class="col s3">
				<a
					href="${pageContext.request.contextPath}/roominformation/${room.id}">
					<mytaglib:i18n key="roomSearch.moreRoomDetails" />
				</a>
			</div>
		</div>
		<div class="collapsible-body">
			<div class="row">
				<c:forEach var="photo" items="${entry.value.list}"
					varStatus="loopCounter">
					<div class="col s2">
						<img class="materialboxed" width="100%" src="${photo.link}">
					</div>

					<%-- <c:if test="${loopCounter.count-1 mod 5 eq 0} "></div> <div class="row"></c:if> --%>

				</c:forEach>
				<sec:authorize access="!isAnonymous()">
					<div class="col s2 center">
						<div class="row">
							<form:form method="POST" enctype="multipart/form-data"
								action="${baseUrl}">
								<div class="file-field input-field">
									<div class="btn">
										<span><mytaglib:i18n key="file" /></span> <input type="file"
											name="file">
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text">
									</div>
									<!-- <input class="file-path validate" type="text"> -->
									<input name="roomid" type="hidden" value="${room.id}">
									<button class="btn waves-effect waves-light" type="submit"
										value="Upload">
										<mytaglib:i18n key="upload" />
										<i class="material-icons right">cloud_upload</i>
									</button>
								</div>
							</form:form>
						</div>
					</div>
					<div class="col s2">
						<h5><c:out value="${error}" /></h5>
					</div>
				</sec:authorize>
				<c:if test="${entry.value.totalCount eq 0}">
					<div class="col s10">
						<div class="caption center-align">
							<h4>
								<mytaglib:i18n key="noPhotos" />
								<c:out value="${room.number}" />
							</h4>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</c:forEach>
</ul>