<?php
  global $base_path; $xml = $_SESSION['xml_arg'];

  $site_fields = array();

  if ($site_name) {
    $site_fields[] = check_plain($site_name);
  }
  if ($site_slogan) {
    $site_fields[] = check_plain($site_slogan);
  }
  $site_title = implode(' ', $site_fields);
  if ($site_fields) {
    $site_fields[0] = '<span>'. $site_fields[0] .'</span>';
  }
  $site_html = implode(' ', $site_fields);
  if($is_front){ $class = "home"; } else{$class = $body_classes . " " . classify($title);}
?>
<?php /*
  <?php print '<?xml version="1.0" encoding="UTF-8" standalone="yes"?>'."\n"; ?> */
//  header("Content-type: text/xml");
?>
<!DOCTYPE ROOT_XML_ELEMENT [ <!ENTITY copy  "&#169;"> <!ENTITY nbsp  "&#160;"> ]>


<?php print '<?xml-stylesheet href="' . $base_path . drupal_get_path('module', 'cacure_x').'/xsltforms/xsltforms.xsl" type="text/xsl"?>'."\n"; ?>
<?php echo '<?css-conversion no?>' ?>
<?php echo '<?xsltforms-options debug="no"?>' ?>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ev="http://www.w3.org/2001/xml-events" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xform="http://www.w3.org/2002/xforms">
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
  <!--[if IE]>
    <link type="text/css" rel="stylesheet" media="all" href="<?php print $base_path . $directory; ?>/ie.css" >
  <![endif]--><title><?php print $head_title; ?></title>

    <?php print $head; ?>
    <?php print $styles; ?>
    <?php print $setting_styles; ?>
<!--    --><?php //print $scripts; ?>


    <?php if ($local_styles): ?>
      <?php print $local_styles; ?>
    <?php endif; ?>

    <?php echo $xml['head']?>

    <link type="text/css" rel="stylesheet" media="all" href="<?php echo $base_path . drupal_get_path('module', 'cacure_x')?>/xsltforms/custom_styles.css" />
    <script type="text/javascript" src="/misc/jquery.js"></script>
    <script type="text/javascript" src="<?php echo $base_path . drupal_get_path('module', 'cacure_x')?>/xsltforms/xsltforms.js" ></script>
    <script type="text/javascript" src="<?php echo $base_path . drupal_get_path('module', 'cacure_x')?>/xsltforms/dialog_box.js" ></script>
    <script type="text/javascript" src="<?php echo $base_path . drupal_get_path('module', 'cacure_mgmt')?>/js/cacure_mgmt_xf.js" ></script>

    <script type="text/javascript">
      (function ($) {
        $(document).ready(function (){
          $('#block-menu-menu-pvt-account ul.menu a[href*=/my-current-questionnaire]').attr('class', 'active').parent().attr('class', 'leaf active-trail-0');
           
           pathArray = window.location.pathname.split( '/' );
           var id = pathArray['3'];

           $('#block-cacure_mgmt-1 div.form-element .text').removeClass('active');

           $('#block-cacure_mgmt-1 div.form-element #' + id).addClass('active');
           
           $('table.hcitComplexTable').each(function(){
                $(this).find('th:last').attr('colspan', 10);
            });
            $('.menu li a').click(function(){
				menuclicks++;
				if (menuclicks == 1) {
					if (confirm('Are you sure you want to navigate away from this page?' + "\r\n" +
						'Navigating away from this screen will cause any changes to be lost. You can click on \'Save for Later\' at the bottom of the form page before trying to navigate away.' + "\r\n" +
						'Please OK to continue, or Cancel to stay on the current page.')) {
						return true;
					} else {
						return false;
					}
				} else {
					menuclicks = 0;
				}
			});
            //correct buttons
            correctButtons('.hcit-approve-action-group', '.hcit-decline-action-group');
            correctButtons('.hcit-save-for-later-group', '.hcit-submit-action-group');
			/*var content = $('#sidebar-first').html();
			content = content + "\r\n" + '<div class="sidebar-first-spacer"></div>';
			$('#sidebar-first').html(content);*/
            $('.hcitSimpleTable tr').each(function(){
                var items = $(this).find('.xforms-item');
                
                if (items.length == 5)
                {
                    $(this).find('.xforms-itemset').css('width', '100%');
                    items.css({'padding' : 0, 'margin' : 0, 'width' : '90px'});
                }
            });
        });
        
        function correctButtons(main, related)
        {
            main = $(main);
           
            if (main.length)
            {
                main.css('overflow', 'hidden').css('visibility', 'visible');
                related = $(related + ' .xforms-group-content');
                main.find('.xforms-group-content').css({'float' : 'left', 'margin-right' : '10px'});
                related.css({'float' : 'left'});
                main.append(related);
            }
        }
      })(jQuery)
    </script>

    <?php if(isset($head_title)):?><title><?php echo $head_title?></title><?php endif?>

  </head>
<?php
  $pixture_width = theme_get_setting('pixture_width');
  $pixture_width = pixture_validate_page_width($pixture_width);
?>
<body id="pixture-reloaded" class="<?php print $body_classes; ?> <?php print $logo ? 'with-logo' : 'no-logo' ; ?>">
<?php if(team_devel_detect_browser() == 'IE(remove)'):?>
       <script>
        var url = location.protocol + '//' + location.host + '/';
        // You may want to place these lines inside an onload handler
        CFInstall.check({
          mode: "overlay",
          destination: url
        });
      </script>
    <?php endif?>
  <div id="skip-to-content">
    <a href="#main-content"><?php print t('Skip to main content'); ?></a>
  </div>
    <div id="page" class="page-forms">
      <div id="header">
        <div id="head-grey-block">
            <?php if ($header_top_block): ?>
            <div id="header-blocks" class="region region-header">
                <?php print $header_top_block; ?>
            </div> <!-- /#header-blocks -->
            <?php endif; ?>
            <?php if ($header): ?>
                <?php print $header; ?>
            <?php endif; ?>
            <?php if ($site_logo): ?>
              <div id="logo"><?php print $site_logo; ?></div>
            <?php endif; ?>
            <?php if ($header_top): ?>
                <?php print $header_top; ?>
            <?php endif; ?>
            <div id="head-elements">
              <?php if ($search_box): ?>
                <div id="search-box">
                  <?php print $search_box; ?>
                </div> <!-- /#search-box -->
              <?php endif; ?>
              <div id="branding">
                <?php if ($site_name): ?>
                  <?php if ($title): ?>
                    <div id="site-name"><strong><?php print $site_name; ?></strong></div>
                  <?php else: /* Use h1 when the content title is empty */ ?>
                    <h1 id="site-name"><?php print $site_name; ?></h1>
                  <?php endif; ?>
                <?php endif; ?>
                <?php if ($site_slogan): ?>
                  <div id="site-slogan"><em><?php print $site_slogan; ?></em></div>
                <?php endif; ?>
              </div> <!-- /#branding -->
            </div> <!-- /#head-elements -->
        </div> 
    </div> <!--/#header -->
    <div id="main" class="clear-block <?php print $header ? 'with-header-blocks' : 'no-header-blocks' ; ?>">
        <?php if ($left): ?>
            <div id="sidebar-left" class="region region-left">
              <?php print $left; ?>
            </div> <!-- /#sidebar-left -->
        <?php endif; ?>
        <div id="content">
            <div id="content-inner">
                <?php if ($mission): ?>
                  <div id="mission"><?php print $mission; ?></div>
                <?php endif; ?>
                <?php if ($content_top): ?>
                  <div id="content-top" class="region region-content_top">
                    <?php print $content_top; ?>
                  </div> <!-- /#content-top -->
                <?php endif; ?>
                <div id="content-header" class="clearfix">
                  <?php if ($breadcrumb): print $breadcrumb; endif; ?>
                  <a name="main-content" id="main-content"></a>
                  <?php if ($tabs): ?><div class="tabs"><?php print $tabs; ?></div><?php endif; ?>
                  <?php if ($messages): print $messages; endif; ?>
                  <?php if ($help): print $help; endif; ?>
                </div> <!-- /#content-header -->
                <div id="content-area">
                  <?php print $content; ?>
                  <div id="xform-block" class="xform-block block block-<?php echo $xml['block_id']?> auto-width"><div class="xform-block-inner"><?php  echo  $xml['body']?></div></div>
                </div>
                <?php if ($content_bottom): ?>
                  <div id="content-bottom" class="region region-content_bottom">
                    <?php print $content_bottom; ?>
                  </div> <!-- /#content-bottom -->
                <?php endif; ?>
                <?php if ($feed_icons): ?>
                  <div class="feed-icons"><?php print $feed_icons; ?></div>
                <?php endif; ?>
            </div><!-- /#content-inner -->
        </div> <!-- /#content -->
      
      <?php if ($right): ?>
        <div id="sidebar-right" class="region region-right">
          <?php print $right; ?>
        </div> <!-- /#sidebar-right -->
      <?php endif; ?>
    </div> <!-- #main -->
    <div id="footer" class="region region-footer">
      <?php if ($footer): print $footer; endif; ?>
        <div id="footer-message">
          <?php print $footer_message; ?>
        </div> <!-- /#footer-message -->
    </div> <!-- /#footer -->
  </div> <!--/#page -->
  <?php if ($closure_region): ?>
    <div id="closure-blocks" class="region region-closure">
      <?php print $closure_region; ?>
    </div>
  <?php endif; ?>
  <?php print $closure; ?>
</body>
</html>