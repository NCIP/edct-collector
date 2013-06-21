<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<c:set var="appPath" value="${pageContext.request.contextPath}" scope="request"/>
<link href="${appPath}/xsltforms/how-styles.css" rel="stylesheet" type="text/css"/>

<body class="twoColFixLtHdr">
	<div id="container">
		<CENTER>
		<BR><BR><BR><BR><BR><BR>
		<fieldset class="error">
		<legend>ERROR</legend>
		<b><font style="color:red;font-size:16px;">Access denied.</font></b>
		<BR><BR>
		<div><a href="#" onclick="javascript:history.go(-1);">Click here to go back</a></div>
		</fieldset>
		</CENTER>
	</div>
</body>
