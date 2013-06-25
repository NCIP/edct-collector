<?php
global $base_path;
$mpath = $base_path . drupal_get_path('module', 'cacure_mgmt');
$context = $arg['context'];
if (preg_match('/^form/', $_GET['q'])) {
    $is_form_displayed = true;
    $matches = array();
    preg_match('|form\/(.+)\/(.+)$|', $_GET['q'], $matches);
    $current_form_id = $matches[2];
}
else {
    $is_form_displayed = false;
}
?>
<!--<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xhtml="http://www.w3.org/1999/xhtml" xmlns:ajx="http://www.ajaxforms.net/1906/ajx" xmlns:xforms="http://www.w3.org/1902/xforms" xmlns:ev="http://www.w3.org/1901/xml-events" xmlns:xsi="http://www.w3.org/1901/XMLSchema-instance" xmlns:xsd="http://www.w3.org/1901/XMLSchema" version="1.0">-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
  <div class="quest-title">
  <xsl:for-each select="modules/module">

    <xsl:if test="@status='submitted'"><div class="quest-submitted image" style="border: 0; background: none;"></div></xsl:if>
    <xsl:if test="@status='in-progress'"><div class="quest-progress image" style="border: 0; background: none;"></div></xsl:if>
    <xsl:if test="@status='new'"><div class="quest-new image" style="border: 0; background: none;"></div></xsl:if>

  </xsl:for-each>
  </div>
    <div class="quest-help"></div>
    <?php if(isset($arg['message'])):?>
      <div><?php echo $arg['message']?></div>
    <?php else:?>
    <div class="health-element">
      <xsl:for-each select="modules/module">
        <xsl:if test="@status='SUBMITTED'">
          <a href="<?php echo $base_path?>/files/pdf" ><?php echo t('View')?></a>
        </xsl:if>
        <?php if (!$is_form_displayed) { ?>
          <script type="text/javascript">
            jQuery(document).ready(function(){
                jQuery('.sub-head .status').remove();
            });
        </script>
        <div class="element-header">
          <div class="module-name"><!--<span class="hcit-questionnaire-topic">Topic:</span>--><xsl:value-of select="@name"/>
          <!--<span  class="hcit-questionnaire-comment">Time to finish: 10 minutes</span>--></div>
          <div class="status-help">
            <div class="status-progress"><?php echo t(" - in progress") ?></div>
            <div class="status-finished"><?php echo t(' - finished')?></div>
          </div>
        </div>
        <?php } ?>


          <div class="form-element-list">

          <xsl:for-each select="form">
            <div class="form-element">


        
          <xsl:if test='@status="new"'>
            <xsl:element name="div">
              <xsl:attribute name="style"><?php echo ($is_form_displayed ? 'white-space: normal;' : '')  ?></xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text <xsl:value-of select="@status"/></xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()"/>:</span>
                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>form/<?php echo $context?>/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="id">link_<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
                  <?php if ($is_form_displayed) { ?>
                    <xsl:attribute name="style">display: block; z-index: 1000; position: relative; padding: 2px 5px; width: 180px; font-weight: normal; overflow: hidden;</xsl:attribute>
                    <xsl:attribute name="onmouseover">this.style.width = '180px'; this.style.overflow = 'visible';</xsl:attribute>
                    <xsl:attribute name="onmouseout">this.style.width = '180px'; this.style.overflow = 'hidden'; </xsl:attribute>
                  <?php } ?>
	          <xsl:value-of select="@name"/>		
                </xsl:element>
            </xsl:element>
          </xsl:if>


          
          <xsl:if test='@status="in-progress"'>
            <xsl:element name="div" >
              <xsl:attribute name="style"><?php echo ($is_form_displayed ? 'white-space: normal;' : '')  ?></xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text <xsl:value-of select="@status"/></xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()"/>:</span>
                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>form/<?php echo $context?>/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="id">link_<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
                  <?php if ($is_form_displayed) { ?>
                    <xsl:attribute name="style">display: block; z-index: 1000; position: relative; padding: 2px 5px; width: 180px; font-weight: normal; overflow: hidden;</xsl:attribute>
                    <xsl:attribute name="onmouseover">this.style.width = '180px'; this.style.overflow = 'visible';</xsl:attribute>
                    <xsl:attribute name="onmouseout">this.style.width = '180px'; this.style.overflow = 'hidden'; </xsl:attribute>
                  <?php } ?>
                    <xsl:value-of select="@name"/>
                </xsl:element>
            </xsl:element>
          </xsl:if>



          <xsl:if test='@status="submitted"'>
            <xsl:element name="div" >
              <xsl:attribute name="style"><?php echo ($is_form_displayed ? 'white-space: normal;' : '')  ?></xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text <xsl:value-of select="@status"/></xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()"/>:</span>
                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>form/<?php echo $context?>/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="id">link_<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
                  <?php if ($is_form_displayed) { ?>
                    <xsl:attribute name="style">display: block; z-index: 1000; position: relative; padding: 2px 5px; width: 180px; font-weight: normal; overflow: hidden;</xsl:attribute>
                    <xsl:attribute name="onmouseover">this.style.width = '180px'; this.style.overflow = 'visible';</xsl:attribute>
                    <xsl:attribute name="onmouseout">this.style.width = '180px'; this.style.overflow = 'hidden'; </xsl:attribute>
                  <?php } ?>
	          <xsl:value-of select="@name"/>
                </xsl:element>
            </xsl:element>
          </xsl:if>

          <xsl:if test='@status="approved"'>
            <xsl:element name="div" >
              <xsl:attribute name="style"><?php echo ($is_form_displayed ? 'white-space: normal;' : '')  ?></xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text <xsl:value-of select="@status"/></xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()"/>:</span>
                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>form/<?php echo $context?>/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="id">link_<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
                  <?php if ($is_form_displayed) { ?>
                    <xsl:attribute name="style">display: block; z-index: 1000; position: relative; padding: 2px 5px; width: 180px; font-weight: normal; overflow: hidden;</xsl:attribute>
                    <xsl:attribute name="onmouseover">this.style.width = '180px'; this.style.overflow = 'visible';</xsl:attribute>
                    <xsl:attribute name="onmouseout">this.style.width = '180px'; this.style.overflow = 'hidden'; </xsl:attribute>
                  <?php } ?>
	          <xsl:value-of select="@name"/>
                </xsl:element>
            </xsl:element>
          </xsl:if>

          <xsl:if test='@status="completed"'>
            <xsl:element name="div" >
              <xsl:attribute name="style"><?php echo ($is_form_displayed ? 'white-space: normal;' : '')  ?></xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text <xsl:value-of select="@status"/></xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()"/>:</span>
                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>form/<?php echo $context?>/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="id">link_<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
                  <?php if ($is_form_displayed) { ?>
                    <xsl:attribute name="style">display: block; z-index: 1000; position: relative; padding: 2px 5px; width: 180px; font-weight: normal; overflow: hidden;</xsl:attribute>
                    <xsl:attribute name="onmouseover">this.style.width = '180px'; this.style.overflow = 'visible';</xsl:attribute>
                    <xsl:attribute name="onmouseout">this.style.width = '180px'; this.style.overflow = 'hidden'; </xsl:attribute>
                  <?php } ?>
	          <xsl:value-of select="@name"/>
                </xsl:element>
            </xsl:element>
          </xsl:if>

          <div class="edit">
              <xsl:element name="a" >
                <xsl:attribute name="href"><?php echo $base_path?>form/<?php echo $context?>/<xsl:value-of select="@id"/></xsl:attribute>
                <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:choose>
                    <xsl:when test='@status="new"'><?php echo t('Add info')?></xsl:when>
                    <xsl:otherwise><?php echo t('Edit info')?></xsl:otherwise>
                  </xsl:choose>
            </xsl:element>
          </div>
	      
         </div>
          </xsl:for-each>
          <?php if ($is_form_displayed) { ?>
          <script type="text/javascript">
          document.getElementById('link_<?php echo $current_form_id;?>').style.color = '#fff';
          document.getElementById('link_<?php echo $current_form_id;?>').style.padding = '2px 5px';
          document.getElementById('link_<?php echo $current_form_id;?>').style.background = '#476E30';
          </script>
          <?php } ?>
        </div>

      </xsl:for-each>
    </div>
<?php endif;?>
  </xsl:template>
</xsl:stylesheet>

