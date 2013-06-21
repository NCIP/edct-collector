<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%-- setup common parameters --%>
<tiles:importAttribute name="activePage" scope="request"/>
<%-- Main content --%>
<div id="content">
<tiles:insertAttribute name="content-sidebar"/>
<div id="mainContent">
<tiles:insertAttribute name="content-menu"/>
<tiles:insertAttribute name="content"/>
</div><!-- end #mainContent -->
<!-- This clearing element should immediately follow the #mainContent div in order to force the #container div to contain all child floats -->
<br class="clearfloat" />
</div><!-- end #content -->
