<?php
  global $base_path;
  $mpath = $base_path . drupal_get_path('module', 'cacure_mgmt');
  $context = caCure_get_contexts();
  $sname = 'http://' . $_SERVER['SERVER_NAME'] . '/';
?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
  <link href="<?php echo $mpath?>/css/management-ajax-default.css" media="all" rel="stylesheet" type="text/css"/>
  <h2>My Recently Submitted Quarterly Report Sections</h2>
  <div class="module-body">
    <?php if(isset($arg['message'])): ?>
      <div class="message"><?php echo $arg['message']?></div>
    <?php else:?>

		<div class="module-name"><?php echo $arg['module_name']?></div>
		<div class="module-submition-date">
                    <?php echo $arg['submitted_date']?>
		</div>

         <div class="edit">
            <xsl:element name="a" >
              <xsl:attribute name="href">/files/module/<?php echo $context?></xsl:attribute>
              <xsl:attribute name="class">module-view-print-information</xsl:attribute>
              <xsl:attribute name="target">_blank</xsl:attribute>
              <?php echo t('View')?> | <img src="<?php echo $sname . drupal_get_path('theme', 'acquia_marina') . '/images/printer.png'?>" alt="Image"/>
            </xsl:element>
          </div>
    <?php endif?>
  </div>

  </xsl:template>
</xsl:stylesheet>
