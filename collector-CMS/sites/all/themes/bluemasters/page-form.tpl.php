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
  <?php global $base_url;?>
   <meta http-equiv="X-UA-Compatible" content="IE=8" />
  <title><?php print $head_title; ?></title>

    <?php print $head; ?>
    <?php print $styles; ?>
    <?php print $setting_styles; ?>
<!--  --><?php //print $scripts; ?>

    <?php if ($local_styles): ?>
      <?php print $local_styles; ?>
    <?php endif; ?>

    <?php echo $xml['head']?>

    <link type="text/css" rel="stylesheet" media="all" href="<?php echo $base_path . drupal_get_path('module', 'cacure_x')?>/xsltforms/custom_styles.css" />
    <script type="text/javascript" src="/misc/jquery.js" ></script>
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
<body  class="<?php print $body_classes; ?>">
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

<div id="page" class="page-forms">

<div id="header-top"><!--header-top-->

  <div id="header-top-inside" class="clearfix">
    <?php if ($logged_in): ?>
    <h2 class="user-name">Hello <?php print l(getName($user->uid), 'user/edit'); ?></h2>
    <!-- Displays User Name when Logged in -->
  <?php endif; ?>
    <div id="header-top-inside-left">
      <div id="header-top-inside-left-content"><?php print $header_top; ?> </div>
    </div>
  </div>
</div><!--/header-top-->

<div id="wrapper">

	<div id="header" class="clearfix">
	
	    <div id="logo">
<?php
            // Prepare header
  $site_fields = array();
  if ($site_name) {
    $site_fields[] = check_plain($site_name);
  }
  if ($site_slogan) {
    $site_fields[] = check_plain($site_slogan);
  }
  $site_title = implode(' ', $site_fields);
  if ($site_fields) {
    $site_fields[0] = '<span>' . $site_fields[0] . '</span>';
  }
  $site_html = implode(' ', $site_fields);

  if ($logo || $site_title) {
    print '<a href="' . check_url($front_page) . '" title="' . $site_title . '">';
    if ($logo) {
      print '<img src="' . check_url($logo) . '" alt="' . $site_title . '" id="logo-image" />';
    }
    print '<div style="display:none">' . $site_html . '</div></a>';
  }
  ?>
      </div> <!--logo-->

  <div id="header-reg">
    <?php print $header; ?>
  </div>
	    
	     <div id="navigation">
	    	<?php //if (isset($primary_links)) { ?><?php //print theme('links', $primary_links, array('class' =>'links', 'id' => 'primary-links')) ?><?php //} ?>
	        <?php print menu_tree($menu_name = 'primary-links'); ?>
	    </div><!--navigation-->
	
	</div><!--header-->

<div id="main-area" class="clearfix">

<div id="main-area-inside" class="clearfix">

    <div id="main"  class="inside clearfix">  
    	
		<?php print $messages; ?>
        <?php print $tabs; ?>
        <?php print $tabs2; ?>
        <?php print $content; ?>
      <div id="xform-block" class="xform-block block block-<?php echo $xml['block_id']?>">
        <div id="content-top">
            <?php print $content_top;?>
    	</div>
        <div class="xform-block-inner">
            <?php  echo  $xml['body']?>
        </div>
      </div>
    </div><!--main-->

    <div id="right" class="clearfix">
        <?php print theme('grid_row', $sidebar_first, 'sidebar-first', 'nested', $sidebar_first_width); ?>
    	<?php print $right;?>
    </div><!--right-->
    
</div>

</div><!--main-area-->
</div><!-- /#wrapper-->


<div id="footer-bottom">
    <div id="footer-bottom-inside" class="clearfix">
    	<div style="float:left">
    		<?php print $footer_message;?> 
    	</div>
    	<div style="float:right">
	        <?php if (isset($secondary_links)) : ?>
	          <?php print theme('links', $secondary_links, array('class' => 'links secondary-links')) ?>
	        <?php endif; ?>      	
    	</div>
    </div>
</div>
    

</div><!-- /page-->
<?php print $closure ?>
</body>
</html>
