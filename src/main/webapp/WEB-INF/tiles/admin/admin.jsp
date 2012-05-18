<%@ include file="/WEB-INF/includes/taglibs.jsp"%>

<div>
	<ol id="admin-links">
			<li><a href="${appPath}/admin/uploadModule.form" >Upload a module</a></li>
			<li><a href="${appPath}/admin/couch/trancate.do" >Truncate CouchDB</a></li>
			<li><a href="${appPath}/admin/directForms.form" >Generate and Save a Form</a></li>
			<li><a href="${appPath}/admin/workflow/configure.do">Customize Workflow</a></li>
			<li><a href="${appPath}/admin/registration.form">Manage Users</a></li>		
	</ol>
</div>