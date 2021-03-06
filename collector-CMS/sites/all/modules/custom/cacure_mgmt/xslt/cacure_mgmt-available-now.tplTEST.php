<?php  global $base_path; $mpath = $base_path . drupal_get_path('module', 'cacure_mgmt');?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
  <link href="<?php echo $mpath?>/css/management-ajax-default.css" media="all" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="<?php echo $mpath?>/js/confirmLeave.js"/>
  <div class="quest-title">
  <xsl:for-each select="modules/module">

    <xsl:if test="@status='submitted'"><div class="quest-submitted image"><img src="<?php echo $mpath?>/image/progress-health.png" alt="Image"/></div></xsl:if>
    <xsl:if test="@status='in-progress'"><div class="quest-progress image"><img src="<?php echo $mpath?>/image/progress-health.png" alt="Image"/></div></xsl:if>
    <xsl:if test="@status='new'"><div class="quest-new image"><img src="<?php echo $mpath?>/image/new-health.png" alt="Image"/></div></xsl:if>

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

        <div class="element-header">
          <div class="module-name"><span class="hcit-questionnaire-topic">Topic:</span><xsl:value-of select="@name"/></div>
          <div class="status-help">
            <div class="status-progress"><img src="<?php echo $mpath?>/image/progress.png"/><?php echo t(" - in progress") ?></div>
            <div class="status-finished"><img src="<?php echo $mpath?>/image/finish.png"/><?php echo t(' - finished')?></div>
          </div>
        </div>


        <div class="form-element-list">
          <xsl:for-each select="form">
            <div class="form-element">


        <xsl:if test="position() = 1">

          <xsl:element name="div" >
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text</xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Start:</span> 

                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>editform/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
	          <xsl:value-of select="@name"/>		
                </xsl:element>

          </xsl:element>

          
          <div class="form-new image"><img src="<?php echo $mpath ?>/image/progress.png"/></div>
          <div class="edit">
            <xsl:element name="a" >
              <xsl:attribute name="href"><?php echo $base_path?>editform/<xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
	      <?php echo t('View')?>
            </xsl:element>
          </div>
	      </xsl:if>
        <xsl:if test="position() != 1">
          <xsl:if test='@status="new"'>

            <xsl:element name="div" >
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text</xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()-1"/>:</span>

                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>editform/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
	          <xsl:value-of select="@name"/>		
                </xsl:element>

            </xsl:element>

            <div class="form-new image"><img src="<?php echo $mpath ?>/image/progress.png"/></div>

          </xsl:if>


          
          <xsl:if test='@status="in-progress"'>

            <xsl:element name="div" >
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text</xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()-1"/>:</span>
                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>editform/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
	          <xsl:value-of select="@name"/>		
                </xsl:element>

            </xsl:element>
         
            <div class="in-progress image"><img src="<? echo $mpath?>/image/progress.png"/></div>
          </xsl:if>



          <xsl:if test='@status="submitted"'>
            <xsl:element name="div" >
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text</xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()-1"/>:</span>

                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>editform/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
	          <xsl:value-of select="@name"/>		
                </xsl:element>

            </xsl:element>

            <div class="submitted image"><img src="<?php echo $mpath?>/image/progress.png"/></div>

          </xsl:if>

          <xsl:if test='@status="completed"'>

            <xsl:element name="div" >
              <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
              <xsl:attribute name="class">text</xsl:attribute>
	      <span class ="hcit-questionnaire-part-number">Part <xsl:value-of select="position()-1"/>:</span>


                <xsl:element name="a" >
                  <xsl:attribute name="href"><?php echo $base_path?>editform/<xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
	          <xsl:value-of select="@name"/>		
                </xsl:element>


            </xsl:element>

            <div class="completed image"><img src="<?php echo $mpath?>/image/finish.png"/></div></xsl:if>

          <div class="edit">
              <xsl:element name="a" >
                <xsl:attribute name="href"><?php echo $base_path?>editform/<xsl:value-of select="@id"/></xsl:attribute>
                <xsl:attribute name="class"><xsl:value-of select="@id"/></xsl:attribute>
                  <xsl:choose>
                    <xsl:when test='@status="new"'><?php echo t('Add info')?></xsl:when>
                    <xsl:otherwise><?php echo t('Edit info')?></xsl:otherwise>
                  </xsl:choose>
            </xsl:element>
          </div>
	      </xsl:if>



            </div>
          </xsl:for-each>
        </div>

      </xsl:for-each>
    </div>
<?php endif;?>
  </xsl:template>
</xsl:stylesheet>

