<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ page import="com.healthcit.how.web.controller.admin.DirectFormsController"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<script src="${appPath}/scripts/directForms.js" type="text/javascript"></script>

<div>
  <table width="650px">
	<tr>
		<td width="25"></td>
		<td width="625px">
		<div id="directFormsWrapper"></div>
			<table width="625" border="0" cellpadding="5px 0px 5px 0px" cellspacing="8px 0px 8px 0px">
				<tr align="left" valign="middle">
					<td align="left" class="FAQQuestion"><b>Access a Form</b></td>
				</tr>
				<tr align="left" valign="middle">
				    <td class="FAQText">
						<form:form method="post" enctype="multipart/form-data" id="directForms" action="directForms.form ">
							<form:errors path="*" cssClass="errorBox" />
							<span class="instructions">Type an existing entity id or generate the new one below</span><br/>
							Entity ID: <input type="text" name="entityid" id="entityid" />
							<hr/>
							<span class="instructions">Specify an Id for the <b>existing</b> Sharing group in order to create an entity in that group</span><br/>
							Sharing Group Id: <input type="text" name="groupId" id="groupId" /><br/>
							<h3>OR</h3>
							<span class="instructions">Specify Group Name in order to create <b>new</b> Entity in the <b>new</b> Sharing Group with this name. ( group names have to be unique)</span><br/>
							Sharing Group Name:  <input type="text" name="group_name" id="group_name" /><br/>
							 <button type="button" id="generate_entity">Generate</button> <br/>
							 <hr/>
							
            				Module Name: <select id="moduleid" name="moduleid"><option></option></select><br/><br/>
            				Form Name: <select id="formid" name="formid"><option></option></select> <br/><br/>
							<input type="submit" disabled="disabled" class="submit"/>
          					
						 </form:form>
					</td>
				</tr>
			</table>
		</td>
	</tr>
  </table>
</div>