<%@ include file="/WEB-INF/includes/taglibs.jsp" %>

<%-- setup common parameters --%>
<tiles:importAttribute name="activePage" scope="request"/>

<%-- Main content --%>
<div id="content">
	<div id="mainContent">
		<!-- Registration Section -->
		<tiles:insertAttribute name="content"/>
	</div><!-- end #mainContent -->

	<!-- This clearing element should immediately follow the #mainContent div in order to force the #container div to contain all child floats -->
	<br class="clearfloat" />
</div><!-- end #content -->
