<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@page trimDirectiveWhitespaces="true"%>
<%-- setup common parameters --%>
<tiles:importAttribute name="title" scope="request"/>
<tiles:importAttribute name="jspLocation" scope="request"/>
<c:set var="appPath" value="${pageContext.request.contextPath}" scope="request"/>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:xf="http://www.w3.org/2002/xforms"
      xmlns:ev="http://www.w3.org/2001/xml-events"
      xmlns:xsd="http://www.w3.org/2001/XMLSchema">

<head>
<title>${title}</title>
	<title>Love-Avon Health Of Women Study</title>
	<link href="${appPath}/xsltforms/how-styles.css" rel="stylesheet" type="text/css"/>
	<link href="${appPath}/styles/styles.css" rel="stylesheet" type="text/css"/>
 	<link href="${appPath}/styles/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css"/>

	<!-- <script type="text/javascript" src="${appPath}/scripts/jquery.js"></script>-->
    <script src="${appPath}/scripts/jquery-1.6.2.min.js"></script>
     <script src="${appPath}/scripts/jquery-ui-1.8.15.custom.min.js"></script>
   <script src="${appPath}/scripts/assignTags.js"></script> 
	
</head>

<body class="twoColFixLtHdr">
<div id="container">
<tiles:insertAttribute name="header"/>

<%-- Main content --%>
<tiles:insertAttribute name="body"/>

<%-- Footer --%>
<tiles:insertAttribute name="footer"/><!-- end #container -->

</div></body>
</html>
