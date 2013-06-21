<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ page import="com.healthcit.how.web.controller.admin.UploadModuleController"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script language="javascript">
function init() {
	var status = document.getElementById("uploadStatus").innerHTML.toString();
	if(status=="error")
	{
		var message="The module that you are trying to upload already exists in the system. All the forms that were removed from the new version will be removed from the server and the data will be lost. If the user had already submitted the forms, then the form will be unsubmitted. Are you sure you would like to proceed?";
		var moduleContext = $('#moduleContext').val();
		var newModuleContext = $('#newModuleContext').val();
		if(moduleContext!= null && newModuleContext!= null && moduleContext != newModuleContext)
		{
			message += "\n" + "The module context " + newModuleContext + " is not the same as the stored context for the module. The original " + moduleContext +" context will be preserved"; 		
		}
		var confirm = window.confirm(message);
		if (!confirm)
		{
			window.location = "uploadModule.form";
			//$('#uploadModule').submit();
		}

	}

}
window.onload = init; 
</script>
<div>
  <table width="650px"> 
	<tr>
		<td width="25"></td>
		<td width="625px">
		<form:form modelAttribute="<%=UploadModuleController.MODULE%>" method="post" id="uploadModule">
		<input type="hidden" name="saveTags" id="saveTags" value = "yes"/>
		<form:hidden path="context"/>
<%-- 	<input type="hidden" name="dirName" value="${uploadStatus.tmpLocation}"/>
		<input type="hidden" name="archiveName" value="${uploadStatus.fileName}"/>
		<input type="hidden" name="moduleContext" id="moduleContext" value="${uploadStatus.moduleContext}"/>
		<input type="hidden" name="newModuleContext" id="newModuleContext" value="${uploadStatus.newModuleContext}"/>
		<input type="hidden" name="isNew" value="${uploadStatus.isNew}"/>
--%>

		<div id="uploadStatus">${sessionScope.uploadStatus.status}</div>
			<table width="625" border="0" cellpadding="5px 0px 5px 0px" cellspacing="8px 0px 8px 0px">
			<tr><td>Form Name</td><td>Tag Name</td>
		    </tr>
			<c:forEach var="form" items="${module.forms}" varStatus="index">
			<tr>
			<td>${form.name}
			</td>
			<td>
			<c:choose>
			<c:when test="${!empty tags}">
			<form:select path="forms[${index.index}].tag" name="tags" id="tags_${index.index}">
			<form:option value="">Select</form:option>
			<c:forEach var="tag" items="${tags}">
			  <c:if test="${not empty tag.id}">
				<c:choose>
				<c:when test="${form.tag.id eq tag.id}">
				<form:option value="${tag.id}" selected="yes">${tag.id}</form:option>
				</c:when>
				<c:otherwise>
				<form:option value="${tag.id}">${tag.id}</form:option>
				</c:otherwise>
				</c:choose>
			  </c:if>
			</c:forEach>
			</form:select>
			</c:when>
			<c:otherwise>
			<form:input type="text" path="forms[${index.index}].tag" value="${form.name}" length="100" size="80"/>
			</c:otherwise>
			</c:choose>
			<input onClick="dialog('createNewTag', null, 'tags_${index.index}',  {height: 110, width: 1000, modal: true, closeOnEscape: true, show: 'slide'}, true, 'closeCreateTag');" type="button" value="Create Tag"/>
			</td>
			</tr>
			</c:forEach>
			</table>
			<input type="submit" name="Save" />
		</form:form>
		</td>
	</tr>
  </table>
</div>
<div id="createNewTag" title="Create New Tag" style="display: none; width: 1000px; height: 100px; overflow: scroll;"><input type="text" name="newTag" size="80" id="newTag"/><input type="submit" name="create" value="Create" onclick="addNewTag(); closeDiv('createNewTag')"/></div>