<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp"%>

<div>
  <table width="650px">
	<tr>
	<td width="625px"><h2>The form returned:</h2>	
		${message} 
	</td>
	</tr>
	<tr>
		<td width="625px"><a href="${appPath}/admin/directForms.form" >Submit a New Form</a></td>
	</tr>
	<tr>
		<td width="625px"><a href="${appPath}/admin/admin.view" >Return to Admin</a></td>
	</tr>
	
	
	
  </table>
</div>