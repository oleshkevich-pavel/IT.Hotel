<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytaglib" uri="my-custom-tags-uri"%>

<c:set var="baseUrl" value="${pageContext.request.contextPath}/upload" />

<form method="POST" enctype="multipart/form-data" action="${baseUrl}">
	<div class="file-field input-field">
		<div class="btn">
			<span><mytaglib:i18n key="file" /></span> <input type="file"
				name="file">
		</div>
		<div class="file-path-wrapper">
			<input class="file-path validate" type="text">
		</div>
		<button class="btn waves-effect waves-light right" type="submit"
			value="Upload">
			<mytaglib:i18n key="upload" />
			<i class="material-icons right">cloud_upload</i>
		</button>
	</div>
</form>

<h5><mytaglib:i18n key="fileUrl" />:</h5> <c:out value="${message}" />
<br>
<img src="${message}">