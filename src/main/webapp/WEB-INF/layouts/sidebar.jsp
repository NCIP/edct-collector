<%--L
  Copyright HealthCare IT, Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/edct-collector/LICENSE.txt for details.
L--%>

<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<tiles:importAttribute name="content"/>
<tiles:importAttribute name="menuList"/>

<div id="sidebar1">
	<!-- Start of Content Page (if applicable) -->
	<tiles:insertAttribute name="content"/>
	<!-- End of Content Page -->
	<!-- Start of Menu -->
	<tiles:useAttribute id="list" name="menuList" classname="java.util.List"/>

	<c:if test="${ not empty list }">
		<div id="left-menu">
			<div class="lh_menu">
				<c:forEach items="${ list }" var="menuItem">
					<div class="lh_menu_element">
						<a href="${ appPath }${ menuItem.link }">${ menuItem.value }</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
	<!-- End of Menu -->
</div>
<!-- end #sidebar1 -->