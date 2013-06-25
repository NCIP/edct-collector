<?php  global $base_path; $mpath = $base_path . drupal_get_path('module', 'cacure_mgmt'); $context = caCure_get_contexts();?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
  <link href="<?php echo $mpath?>/css/management-ajax-default.css" media="all" rel="stylesheet" type="text/css"/>
  <h2>My Submitted Quarterly Report Sections</h2>
  <div class="sub-head">
    <span class="current">Current Quarterly Report Section</span>
    <span class="status"><img border="0" alt="Status Definition" src="/sites/all/themes/how/images/box_checked.png" />  =  Finished</span>
  </div>

  <div class="quest-title">
  <xsl:for-each select="modules/module">

    <xsl:if test="@status='submitted'"><div class="quest-submitted image"><img src="<?php echo $mpath?>/image/progress-health.png" alt="Image"/></div></xsl:if>
    <xsl:if test="@status='in-progress'"><div class="quest-progress image"><img src="<?php echo $mpath?>/image/progress-health.png" alt="Image"/></div></xsl:if>
    <xsl:if test="@status='new'"><div class="quest-new image"><img src="<?php echo $mpath?>/image/new-health.png" alt="Image"/></div></xsl:if>

  </xsl:for-each>

  </div>
    <div class="quest-help"></div>
    <div class="health-element">
    <?php if(isset($arg['message'])): ?>
      <div class="message"><?php echo $arg['message']?></div>
    <?php else:?>
      <xsl:for-each select="modules/module">

        <div class="element-header">
          <div class="module-name"><xsl:value-of select="@name"/></div>

		  <div class="module-submition-date">
			<xsl:choose>
			<xsl:when test="@dateSubmitted  != '' ">
			   <!-- Submitted on  < ?php echo date('m/d/y',strtotime("<xsl:value-of select='substring(@dateSubmitted, 1,10)'/>"))? > -->
			   Submitted on <?php echo $arg['submitted_date']?> 
 
           </xsl:when>
			<xsl:otherwise>
			  Submission date unknown
			</xsl:otherwise>
		  </xsl:choose>
		  </div> 

		  <div class="view-and-pring">
              <xsl:element name="a" >
                <xsl:attribute name="href">/files/module/<?php echo $context?></xsl:attribute>
                <xsl:attribute name="class">module-view-print-information</xsl:attribute>
                <xsl:attribute name="target">_blank</xsl:attribute>
                <?php echo t('View')?> | <img src="/sites/all/themes/how/images/printer.png" alt="Image"/>
            </xsl:element>
		 </div>
        </div>

      </xsl:for-each>
<?php endif?>
    </div>
  </xsl:template>
</xsl:stylesheet>

