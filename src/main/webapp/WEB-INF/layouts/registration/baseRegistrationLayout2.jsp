<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<%-- setup common parameters --%>
<tiles:importAttribute name="activePage" scope="request"/>

<!-- Javascript -->
<script type='text/javascript' src='${ appPath }/dwr/engine.js'></script>
<script type='text/javascript' src='${ appPath }/dwr/util.js'></script>
<script type='text/javascript' src='${ appPath }/dwr/interface/RegistrationService.js'></script>
<script src="${appPath}/scripts/jquery.js" type="text/javascript"></script>
<script src="${appPath}/scripts/manageUsers.js" type="text/javascript"></script>

<%-- Main content --%>
<div id="content">
	<div id="mainContent">
		<!-- Registration Section -->
		<tiles:insertAttribute name="regSection"/>
	</div><!-- end #mainContent -->

	<!-- This clearing element should immediately follow the #mainContent div in order to force the #container div to contain all child floats -->
	<br class="clearfloat" />
</div><!-- end #content -->
